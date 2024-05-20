import { useState, useEffect } from "react";

import { useNavigate, useParams } from "react-router-dom";

import { SpectacleProps, initialSpectacle } from "../utils/props";
import { fetchSpectacle } from "../utils/api";

function View() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [spectacle, setSpectacle] = useState<SpectacleProps>(initialSpectacle);

    useEffect(() => {
        async function fetchData(id: string | undefined) {
            const res = await fetchSpectacle(id);
            setSpectacle(res);
        }

        fetchData(id);
    }, [id]);

    function handleBack() {
        navigate("/");
    }

    return (
        <>
            <div className="container">
                <div className="row">
                    <div className="col-md-12">
                        <h1>Spectacle Details</h1>

                        <table className="table">
                            <thead>
                                <tr>
                                    <th>SpectacleId</th>
                                    <th>ArtistName</th>
                                    <th>SpectaclePlace</th>
                                    <th>SpectacleDate</th>
                                    <th>SeatsAvailable</th>
                                    <th>SeatsSold</th>
                                </tr>
                            </thead>

                            <tbody>
                                {spectacle && (
                                    <tr>
                                        <td>{spectacle.spectacleId}</td>
                                        <td>{spectacle.artistName}</td>
                                        <td>{spectacle.spectacleDate}</td>
                                        <td>{spectacle.spectaclePlace}</td>
                                        <td>{spectacle.seatsAvailable}</td>
                                        <td>{spectacle.seatsSold}</td>                          
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="container d-flex justify-content-center">
                    <div>
                        <button className="btn btn-primary" onClick={handleBack}>Back To Home</button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default View;