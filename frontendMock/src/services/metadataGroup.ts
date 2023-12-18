import { AxiosError, AxiosResponse } from "axios";
import { IMetadataGroup } from "../types/metadatagroup";
import api from "./api";

/**
 *
 * @param slug slug of laptop
 * @returns
 */

export function addMetadataGroup(
  metadataGroup: IMetadataGroup
): Promise<IMetadataGroup> {
  return api
    .post("/metadata-group/create", {
      name: metadataGroup.name,
    })
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}

export function deleteMetadataGroup(groupId: string): Promise<string> {
  return api
    .delete(`/metadata-group/delete/${groupId}`)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}
export function getMetadataWithMetadataGroup(
  slug: string
): Promise<IMetadataGroup[]> {
  return api
    .get(`/metadata-group/get/${slug}`)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}

export function editMetadataGroup(
  group: IMetadataGroup
): Promise<IMetadataGroup> {
  return api
    .put(`/metadata-group/${group.id}`, group)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}

export function getAllMetaDataGroup(): Promise<IMetadataGroup[]> {
  return api
    .get(`/metadata-group/list`)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}

export function getMetadataGroupById(groupId: string): Promise<IMetadataGroup> {
  return api
    .get(`/metadata-group/getById/${groupId}`)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}
