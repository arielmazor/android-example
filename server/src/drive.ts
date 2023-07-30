import axios from "axios";
import { Application, Request, Response } from "express"
import { OAuth2Client } from "google-auth-library";
import { drive_v3, google } from "googleapis";
import QueryString from "qs";

interface IFile {
  id: string,
  name: string,
  thumbnail: string,
  MIME: string
}

export function drive(api: Application) {
  api.get("/files", [], async (req: Request, res: Response) => {
    const access_token = await axios.post("https://www.googleapis.com/oauth2/v4/token", {
      client_id: process.env.CLIENT_ID,
      client_secret: process.env.CLIENT_SECRET,
      refresh_token: req.query.refresh_token,
      grant_type: "refresh_token"
    })

    const headers = {
      'Authorization': `Bearer ${access_token.data.access_token}`
    }

    const options = {
      orderBy: "modifiedTime desc",
      pageSize: 8,
      q: "trashed = false and 'me' in owners and (mimeType = 'application/vnd.google-apps.folder' or  mimeType = 'application/vnd.google-apps.file' or  mimeType = 'application/vnd.google-apps.document')"
    }

    const response = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(options), { headers })

    var files: IFile[] = [];
    const thumbnail = await axios.get(`https://drive.google.com/thumbnail?sz=w640&id=1RGvW4qKK--hHRdI_P0KrGGAbuNPox6GW72x9YgyIITw`, { headers })
    console.log(thumbnail.data)
    if (response.data.files != null) {
      await Promise.all(response.data.files.map(async (file: any) => {
        const thumbnail = await getThumbnail(file.id, headers)
        const _file = {
          name: file.name!,
          id: file.id!,
          thumbnail,
          MIME: file.mimeType!
        }
        files.push(_file)
      }))
    }

    return res.status(200).json(files);
  })

  api.get("/thumbnail", [], async (req: Request, res: Response) => {
    const access_token = await axios.post("https://www.googleapis.com/oauth2/v4/token", {
      client_id: process.env.CLIENT_ID,
      client_secret: process.env.CLIENT_SECRET,
      refresh_token: req.query.refresh_token,
      grant_type: "refresh_token"
    })

    const headers = {
      'Authorization': `Bearer ${access_token.data.access_token}`
    }

    const response = await axios.get(`https://drive.google.com/thumbnail?sz=w640&id=${req.query.fileId}}`, { headers })

    return res.status(200).json(response.data);
  })
}

const getThumbnail = async (id: string, headers: any): Promise<string> => {
  const file = await axios.get(`https://www.googleapis.com/drive/v3/files/${id}?fields=thumbnailLink`, { headers })
  return file.data.thumbnailLink || "";
}