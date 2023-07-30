import { Request, Response } from "express";
import axios from "axios";
import QueryString from "qs";
import { google } from "googleapis";

interface GoogleTokensResult {
  access_token: string;
  expires_in: Number;
  refresh_token: string;
  scope: string;
  id_token: string;
}

async function getGoogleOAuthTokens({
  code,
}: {
  code: string;
}): Promise<GoogleTokensResult | any> {
  const url = "https://oauth2.googleapis.com/token";

  const values = {
    code,
    client_id: process.env.CLIENT_ID,
    client_secret: process.env.CLIENT_SECRET,
    redirect_uri: process.env.REDIRECT_URI,
    grant_type: "authorization_code",
  };
  try {
    const res = await axios.post<GoogleTokensResult>(
      url,
      QueryString.stringify(values),
      {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      }
    );
    console.log(res.data)
    return res.data;
  } catch (error: any) {
    console.error(error.response.data.error);
    throw new Error(error.message);
  }
}

interface GoogleUserResult {
  id: string;
  email: string;
  verified_email: boolean;
  name: string;
  given_name: string;
  family_name: string;
  picture: string;
  locale: string;
}

export async function getGoogleUser({ id_token, access_token }: any): Promise<GoogleUserResult> {
  try {
    const res = await axios.get<GoogleUserResult>(
      `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${access_token}`,
      {
        headers: {
          Authorization: `Bearer ${id_token}`,
        },
      }
    );
    return res.data;
  } catch (error: any) {
    console.error(error, "Error fetching Google user");
    throw new Error(error.message);
  }
}


export async function googleOauthHandler(req: Request, res: Response) {
  const code = req.query.code as string;

  try {
    const tokens = await getGoogleOAuthTokens({ code });
    console.log({ tokens })
    const values = {
      refresh_token: tokens.refresh_token,
      access_token: tokens.access_token
    }
    const rootUrl: string = process.env.ORIGIN as string;
    res.redirect(`${rootUrl}?${QueryString.stringify(values)}`);
  } catch (error) {
    console.error(error, "Failed to authorize Google user");
    return res.redirect(`${process.env.ORIGIN}/oauth/error`);
  }
}