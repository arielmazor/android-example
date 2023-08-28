import axios from "axios";
import { Application, Request, Response } from "express"
import QueryString from "qs";

enum eOrderBy {
  modifiedTime = "modifiedTime desc",
  modifiedTimeAsc = "modifiedTime",
  name = "name_natural desc",
  nameAsc = "name_natural",
  OpenedTime = "viewedByMeTime desc",
  OpenedTimeAsc = "viewedByMeTime"
}


interface IFile {
  id: string,
  name: string,
  thumbnailLink: string,
  mimeType: string
}

export function drive(api: Application) {
  api.get("/drive/folder", [], async (req: Request, res: Response) => {
    try {
      const { refreshToken, pageToken, folderId, orderBy } = getQueryParams(req.query)
      console.log(JSON.stringify(getQueryParams(req.query), null, 2))
      const headers = await authorize(refreshToken);
      const files = await getFolder(headers, orderBy, pageToken, folderId);

      res.status(200).json({ nextPageToken: files.nextPageToken, files: files.files })
    } catch (err: any) {
      console.error(JSON.stringify(err))
      res.status(404);
    }
  })

  api.get("/drive/more", [], async (req: Request, res: Response) => {
    try {
      const { refreshToken, orderBy, pageToken, folderId } = getQueryParams(req.query);
      const headers = await authorize(refreshToken);
      const files = await getFiles(headers, orderBy, pageToken, folderId)

      res.status(200).json(files)
    } catch (err: any) {
      console.error(err.response.data.error)
      res.status(404);
    }
  })


}

const getQueryParams = (query: any) => {
  const refreshToken = query.refreshToken as string;
  const orderBy = query.orderBy as eOrderBy
  var pageToken: string | undefined = query.pageToken as string | undefined;
  if (pageToken != undefined && pageToken.length == 0) {
    pageToken = undefined
  }
  var folderId: string | undefined = query.folderId as string | undefined;

  if (folderId != undefined && folderId.length == 0) {
    folderId = undefined
  }

  return {
    refreshToken,
    orderBy,
    pageToken,
    folderId
  }
}

const getFolder = async (headers: any, orderBy: eOrderBy, pageToken?: string, folderId?: string): Promise<{ files: IFile[], nextPageToken: string }> => {
  try {
    const query: any = {
      orderBy: `folder, ${orderBy}`,
      q: `trashed = false and 'me' in owners and (${folderMimeType} or ${pdfMimeType} or ${pngMimeType} or ${jpegMimeType} or ${wordMimeType} or ${docsMimeType} or ${slidesMimeType}`,
      fields: "nextPageToken, files(id, name, mimeType, thumbnailLink)",
      pageSize: 10
    }

    if (folderId) {
      query.q += ` and '${folderId}' in parents`;
      query.pageSize = 100
    } else {
      query.q += ' and "root" in parents';
    }
    if (pageToken) {
      query.pageToken = pageToken;
    }

    const filesRes = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(query), { headers })

    var files: IFile[] = filesRes.data.files != null ? filesRes.data.files : [];

    return { files, nextPageToken: filesRes.data.nextPageToken ? filesRes.data.nextPageToken : "" }
  }
  catch (err) { throw err; }
}

const getFiles = async (headers: any, orderBy: eOrderBy, pageToken?: string, folderId?: string): Promise<{ files: IFile[], nextPageToken: string }> => {
  try {
    const query: any = {
      orderBy: `folder, ${orderBy}`,
      q: `trashed = false and 'me' in owners and (${folderMimeType} or ${pdfMimeType} or ${pngMimeType} or ${jpegMimeType} or ${wordMimeType} or ${docsMimeType} or ${slidesMimeType}`,
      fields: "nextPageToken, files(id, name, mimeType, thumbnailLink)",
      pageSize: 10
    }

    if (folderId) {
      query.q += ` and '${folderId}' in parents`;
      query.pageSize = 100
    } else {
      query.q += ' and "root" in parents';
    }
    if (pageToken) {
      query.pageToken = pageToken;
    }
    const filesRes = await axios.get(`https://www.googleapis.com/drive/v3/files?` + QueryString.stringify(query), { headers })

    const files: IFile[] = []

    if (filesRes.data.files != null) {
      filesRes.data.files.forEach((file: IFile) => {
        files.push({
          name: file.name,
          id: file.id,
          thumbnailLink: file.thumbnailLink ? file.thumbnailLink : "",
          mimeType: file.mimeType
        })
      })
    }

    return { files, nextPageToken: filesRes.data.nextPageToken ? filesRes.data.nextPageToken : "" }
  } catch (err: any) {
    console.error("SHit" + JSON.stringify(err))
    throw (err)
  }
}

const authorize = async (refresh_token: string): Promise<any> => {
  try {
    const res = await axios.post("https://www.googleapis.com/oauth2/v4/token", {
      client_id: process.env.CLIENT_ID,
      client_secret: process.env.CLIENT_SECRET,
      refresh_token: refresh_token,
      grant_type: "refresh_token"
    })

    return {
      'Authorization': `Bearer ${res.data.access_token}`
    }
  } catch (err: any) { throw (err) }
}

`trashed = false and 'me' in owners and (mimeType = 'application/pdf' or mimeType = 'image/png' or mimeType = 'image/jpeg' or mimeType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' or mimeType = 'application/vnd.google-apps.document' or mimeType = 'application/vnd.google-apps.presentation' or mimeType='application/vnd.google-apps.folder')`

const folderMimeType = "mimeType = 'application/vnd.google-apps.folder'";
const pdfMimeType = "mimeType = 'application/pdf'";
const pngMimeType = "mimeType = 'image/png'";
const jpegMimeType = "mimeType = 'image/jpeg'";
const wordMimeType = "mimeType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'";
const docsMimeType = "mimeType = 'application/vnd.google-apps.document'";
const slidesMimeType = "mimeType = 'application/vnd.google-apps.presentation')";