import express, { Response } from "express";
import { googleOauthHandler } from "./auth";
import dotenv from "dotenv";
import { drive } from "./drive";
import { google } from "googleapis";
import axios from "axios";

dotenv.config()

const PORT = process.env.PORT as string;

const app = express();

app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,PATCH,DELETE");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
  next();
});


app.get("/access_token", async (req, res: Response) => {
  const tokens = await axios.post("https://www.googleapis.com/oauth2/v4/token", {
    client_id: process.env.CLIENT_ID,
    client_secret: process.env.CLIENT_SECRET,
    refresh_token: "1//03yE7Pw2J-lRNCgYIARAAGAMSNwF-L9IrEZPJYNjImoTylDhgoUiARH694eflp3P29YKTe2VkD0ErCQ_4f7T8jI6YRHt-AnZMgkE",
    grant_type: "refresh_token"
  })

  res.status(200).json({
    accessToken: tokens.data.access_token,
    expirationTime: tokens.data.expires_in
  });
})

drive(app)


app.listen(PORT, () => {
  console.log(`Server listening on ${PORT}`);
});