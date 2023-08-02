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

    const foldersQuery = {
      orderBy: "modifiedTime desc",
      q: `trashed = false and 'me' in owners and ${folderMimeType}`
    }

    const filesQuery = {
      orderBy: "modifiedTime desc",
      pageSize: 8,
      q: `trashed = false and 'me' in owners and (${pdfMimeType} or ${pngMimeType} or ${jpegMimeType} or ${wordMimeType} or ${docsMimeType} or ${slidesMimeType}`
    }

    const foldersRes = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(foldersQuery), { headers })

    var files: IFile[] = [];

    if (foldersRes.data.files != null) {
      foldersRes.data.files.forEach((folder: any) => {
        const _folder = {
          name: folder.name,
          id: folder.id,
          thumbnail: "",
          MIME: folder.mimeType
        }

        files.push(_folder)
      });
    }

    const filesRes = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(filesQuery), { headers })

    if (filesRes.data.files != null) {
      await Promise.all(filesRes.data.files.map(async (file: any) => {
        var thumbnail = ""
        if (file.mimeType.includes("image/")) {
          thumbnail = await getThumbnail(file.id, headers)
        }
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

const folderMimeType = "mimeType = 'application/vnd.google-apps.folder'";
const pdfMimeType = "mimeType = 'application/pdf'";
const pngMimeType = "mimeType = 'image/png'";
const jpegMimeType = "mimeType = 'image/jpeg'";
const wordMimeType = "mimeType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'";
const docsMimeType = "mimeType = 'application/vnd.google-apps.document'";
const slidesMimeType = "mimeType = 'application/vnd.google-apps.presentation')";