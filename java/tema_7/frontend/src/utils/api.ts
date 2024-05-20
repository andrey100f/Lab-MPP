import axios from "axios";
import { AddSpectacleProps, SpectacleProps } from "./props";

const baseUrl = "http://localhost:8080/api/spectacles";

export async function fetchSpectacle(id: string | undefined) {
    try {
        const result = await axios.get(`${baseUrl}/${id}`);
        return Promise.resolve(result.data);
    }
    catch (error) {
        throw new Error(error as string);
    }
}

export async function updateSpectacle(spectacleFields: SpectacleProps) {
    try {
        const res = await axios.put(baseUrl, spectacleFields);
        return Promise.resolve(res.data);
    }
    catch (error) {
        throw new Error(error as string);
    }
}

export async function addSpectacle(spectacleFields: AddSpectacleProps) {
    try {
        const res = await axios.post(baseUrl, spectacleFields);
        return Promise.resolve(res.data);
    }
    catch (error) {
        throw new Error(error as string);
    }
}

export async function fetchSpectacles() {
    try {
        const result = await axios.get(baseUrl);
        return Promise.resolve(result.data);
    }
    catch (error) {
        throw new Error(error as string);
    }
}

export async function deleteSpectacle(spectacleId: number) {
    try {
        const res = await axios.delete(`${baseUrl}/${spectacleId}`);
        return Promise.resolve(res.data);  
    }
    catch (error) {
        throw new Error(error as string);
    }
}
