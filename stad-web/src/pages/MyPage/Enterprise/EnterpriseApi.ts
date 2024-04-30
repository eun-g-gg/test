import axios from "axios";
import { EnterpriseData } from "./EnterprisesEdit";

export async function profileImgUpload(file: File, userId: number) {
  if (!file) return null;
  console.log(file);
  const formData = new FormData();
  formData.append("memberId", userId.toString());
  formData.append("profileImg", file);
  try {
    const res = await axios.post(`/api/user/profile`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("업로드 성공", res);
    return res.data;
  } catch (error) {
    console.error("업로드 실패", error);
  }
}

export async function postProfileEdit(
  userData: EnterpriseData,
  accessToken: string
) {
  try {
    const response = await axios.post("/api/user/update", userData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });
    console.log("Update Success:", response.data);
  } catch (error) {
    console.error("Update Error:", error);
    throw error;
  }
}
export async function GetEnterpriseInfo(userId: number, token: string) {
  const response = await axios.get(`/api/user`, {
    params: {
      userId: userId,
    },
    headers: { Authorization: `Bearer ${token}` },
  });
  return await response.data;
}
