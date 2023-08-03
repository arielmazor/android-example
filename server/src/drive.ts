import axios from "axios";
import { Application, Request, Response } from "express"
import QueryString from "qs";

interface IFolder {
  id: string,
  name: string
}

interface IFile {
  id: string,
  name: string,
  thumbnail: string,
  mimeType: string
}

export function drive(api: Application) {
  api.get("/drive/initial", [], async (req: Request, res: Response) => {
    const headers = await authorize(req.query.refresh_token as string)
    const folders = await getFolders(headers)
    const files = await getFiles(headers, "")
    res.status(200).json({
      foldersNextPageToken: folders.nextPageToken,
      folders: folders.folders,
      filesNextPageToken: files.nextPageToken,
      files: files.files
    });
  })
}

const getFolders = async (headers: any, pageToken?: string): Promise<{ folders: IFolder[], nextPageToken: any }> => {
  const query: any = {
    orderBy: "modifiedTime desc",
    q: `trashed = false and 'me' in owners and ${folderMimeType}`,
    fields: "nextPageToken, files(id, name)"
  }

  if (pageToken) {
    query.pageToken = pageToken;
  } else {
    query.pageSize = 5;
  }

  const foldersRes = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(query), { headers })

  const folders: IFolder[] = foldersRes.data.files || [];
  var nextPageToken = "";
  if (folders.length === 5 && !pageToken) {
    folders.pop();
    nextPageToken = foldersRes.data.nextPageToken
  }

  return { folders, nextPageToken }
}

const getFiles = async (headers: any, pageToken?: string): Promise<{ files: IFile[], nextPageToken: any }> => {
  const query: any = {
    orderBy: "modifiedTime desc",
    pageSize: 8,
    q: `trashed = false and 'me' in owners and (${pdfMimeType} or ${pngMimeType} or ${jpegMimeType} or ${wordMimeType} or ${docsMimeType} or ${slidesMimeType}`,
    fields: "nextPageToken, files(id, name, mimeType, thumbnailLink)"
  }

  if (pageToken) {
    query.pageToken = pageToken;
  }

  const filesRes = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(query), { headers })

  const files: IFile[] = filesRes.data.files ? filesRes.data.files : []

  return { files, nextPageToken: filesRes.data.nextPageToken }
}

const authorize = async (refresh_token: string): Promise<any> => {
  const res = await axios.post("https://www.googleapis.com/oauth2/v4/token", {
    client_id: process.env.CLIENT_ID,
    client_secret: process.env.CLIENT_SECRET,
    refresh_token: refresh_token,
    grant_type: "refresh_token"
  })

  return {
    'Authorization': `Bearer ${res.data.access_token}`
  }
}

const folderMimeType = "mimeType = 'application/vnd.google-apps.folder'";
const pdfMimeType = "mimeType = 'application/pdf'";
const pngMimeType = "mimeType = 'image/png'";
const jpegMimeType = "mimeType = 'image/jpeg'";
const wordMimeType = "mimeType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'";
const docsMimeType = "mimeType = 'application/vnd.google-apps.document'";
const slidesMimeType = "mimeType = 'application/vnd.google-apps.presentation')";