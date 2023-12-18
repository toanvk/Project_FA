import { AxiosError, AxiosResponse } from "axios";
import api from "./api";
import { IReview } from "../types/reiview";

export function getAllReviews(): Promise<IReview[]> {
  return api
    .get(`review/list`)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}

export function getReviewByLaptopId(id: number): Promise<IReview[]> {
  return api
    .get(`/review/getByLaptopId/${id}`)
    .then((response: AxiosResponse) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      throw new Error("Fetch failed");
    });
}
