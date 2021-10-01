export interface DatabaseProperties {
  id: number;
  name: string;
  engine: string;
  createdAt: Date;
}

export type UpstreamDatabaseProperties = Pick<
  DatabaseProperties,
  "name" | "engine"
>;

export interface CredentialProperties {
  username: string;
  password: string;
}

export enum StoreStatus {
  PARTIALLY_LOADED = "PARTIALLY_LOADED",
  FULLY_LOADED = "FULLY_LOADED",
}

export interface HostProperties {
  hostname: string;
  port: number;
}
