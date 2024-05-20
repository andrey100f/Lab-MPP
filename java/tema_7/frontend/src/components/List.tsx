import { useEffect, useState } from "react";

import { NavLink } from "react-router-dom";
import { toast } from "react-toastify";

import { SpectacleProps } from "../utils/props";
import { fetchSpectacles, deleteSpectacle } from "../utils/api";

function List() {
    const [spectacleData, setSpectacleData] = useState<SpectacleProps[]>([]);

    async function fetchData() {
        const res = await fetchSpectacles();
        setSpectacleData(res);
    }

    useEffect(() => {
        fetchData();
    }, []);

    async function handleDelete(spectacleId: number) {
        await deleteSpectacle(spectacleId);
        fetchData();
        toast.success("Spectacle deleted successfully");
    }

    return (
        <div className="container">
            <table className="table table-bordered">
                <thead>
                    <tr>
                        <th>SpectacleId</th>
                        <th>ArtistName</th>
                        <th>SpectaclePlace</th>
                        <th>SpectacleDate</th>
                        <th>SeatsAvailable</th>
                        <th>SeatsSold</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {spectacleData && spectacleData.map((spectacle) => {
                        return (
                            <tr key={spectacle.spectacleId}>
                                <td>{spectacle.spectacleId}</td>
                                <td>{spectacle.artistName}</td>
                                <td>{spectacle.spectacleDate}</td>
                                <td>{spectacle.spectaclePlace}</td>
                                <td>{spectacle.seatsAvailable}</td>
                                <td>{spectacle.seatsSold}</td>
                                <td>
                                    <NavLink to={`/view/${spectacle.spectacleId}`} className="btn btn-success mx-2">View</NavLink>
                                    <NavLink to={`/edit/${spectacle.spectacleId}`} className="btn btn-info mx-2">Edit</NavLink>
                                    <button className="btn btn-danger" onClick={() => handleDelete(spectacle.spectacleId)}>Delete</button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
}

export default List;