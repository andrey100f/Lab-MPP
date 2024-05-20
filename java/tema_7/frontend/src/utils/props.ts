export interface SpectacleProps {
    spectacleId: number;
    artistName: string;
    spectacleDate: string;
    spectaclePlace: string;
    seatsAvailable: number;
    seatsSold: number;
}

export interface AddSpectacleProps {
    artistName: string;
    spectacleDate: string;
    spectaclePlace: string;
    seatsAvailable: number;
    seatsSold: number;
}

export const initialSpectacle = {
    spectacleId: 0,
    artistName: "",
    spectacleDate: "",
    spectaclePlace: "",
    seatsAvailable: 0,
    seatsSold: 0
}

export const addInitialSpectacle = {
    artistName: "",
    spectacleDate: "",
    spectaclePlace: "",
    seatsAvailable: 0,
    seatsSold: 0
}