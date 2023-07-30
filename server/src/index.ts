import express from "express";
import { googleOauthHandler } from "./auth";
import dotenv from "dotenv";
import { drive } from "./drive";
import { google } from "googleapis";

dotenv.config()

const PORT = process.env.PORT as string;

const app = express();

app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,PATCH,DELETE");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
  next();
});


drive(app)


app.listen(PORT, () => {
  console.log(`Server listening on ${PORT}`);
});