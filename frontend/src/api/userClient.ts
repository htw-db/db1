import * as yup from "yup";

import { axiosInstance } from "./client";
import { CredentialProperties } from "../types/models";
import { UserProperties } from "../types/user";

export class UserClient {
  /**
   * Login with credentials
   */
  public static login = (credentials: CredentialProperties) =>
    axiosInstance.post<any>("/auth/signin", credentials);

  /**
   * Get user data
   */
  public static getUser = async (): Promise<UserProperties> => {
    const { data } = await axiosInstance.get<UserProperties>("/user");
    return data;
  };

  /**
   * Search for users
   */
  public static getUsers = async (): Promise<UserProperties[]> => {
    const { data } = await axiosInstance.get<UserProperties[]>("/users");
    return data;
  };
}

export class UserValidation {
  public static tokenSchema = yup.object().shape({
    accessToken: yup.string().required(),
  });

  public static loginSchema = yup.object().shape({
    username: yup
      .string()
      .required("Username is required")
      .matches(/^[a-z0-9]*$/, "Username must contain small letters or digits."),
    password: yup
      .string()
      .min(4, "Password should be of minimum 4 characters length")
      .required("Password is required"),
  });
}
