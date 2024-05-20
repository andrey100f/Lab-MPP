import { useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";

import { SpectacleProps, initialSpectacle } from "../utils/props";
import { fetchSpectacle, updateSpectacle } from "../utils/api";

function Edit() {
    const {id} = useParams();
    const navigate = useNavigate();
    const [spectacle, setSpectacle] = useState<SpectacleProps>(initialSpectacle);
    const [spectacleFields, setSpectacleFields] = useState<SpectacleProps>(initialSpectacle);

    useEffect(() => {
        async function fetchData() {
            const res = await fetchSpectacle(id);
            
            setSpectacleFields(res);
            setSpectacle(res);
        }

        fetchData();
    }, [id]);

    function handleChangeSpectaclesField(e: React.ChangeEvent<HTMLInputElement>) {
        setSpectacleFields({
            ...spectacleFields,
            [e.target.name]: e.target.value
        });
    }

    function handleBack() {
        navigate("/");
    }

    async function handleSubmit(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        e.preventDefault();

        try {
            await updateSpectacle(spectacleFields);
            toast.success("Spectacle updated successfully");
        }
        catch (err) {
            toast.error("Error updating spectacle");
        }

    }

    return (
        <div className="container">
            <h2>Edit Spectacle</h2>

            <form>
                {spectacle && (
                    <>
                        <div className="mb-e mt-3">
                            <label htmlFor="id" className="form-label">ID</label>
                            <input type="text" className="form-control" id="id" name="id" value={spectacleFields.spectacleId} disabled />
                        </div>

                        <div className="mb-3 mt-3">
                            <label htmlFor="artistName" className="form-label">Artist Name</label>
                            <input type="text" className="form-control" id="artistName" placeholder="Enter Artist Name" name="artistName" value={spectacleFields.artistName} onChange={e => handleChangeSpectaclesField(e)} />
                        </div>

                        <div className="mb-3 mt-3">
                            <label htmlFor="spectacleDate" className="form-label">Spectacle Date</label>
                            <input type="date" className="form-control" id="spectacleDate" name="spectacleDate" value={spectacleFields.spectacleDate} onChange={e => handleChangeSpectaclesField(e)} />
                        </div>

                        <div className="mb-3 mt-3">
                            <label htmlFor="spectaclePlace" className="form-label">Spectacle Place</label>
                            <input type="text" className="form-control" id="spectaclePlace" placeholder="Enter Spectacle Place" name="spectaclePlace" value={spectacleFields.spectaclePlace} onChange={e => handleChangeSpectaclesField(e)} />
                        </div>

                        <div className="mb-3 mt-3">
                            <label htmlFor="seatsAvailable" className="form-label">Spectacle Seats Available</label>
                            <input type="number" className="form-control" id="seatsAvailable" placeholder="Enter Spectacle Seats Available" name="seatsAvailable" value={spectacleFields.seatsAvailable} onChange={e => handleChangeSpectaclesField(e)} />
                        </div>

                        <div className="mb-3 mt-3">
                            <label htmlFor="seatsSold" className="form-label">Spectacle Seats Sold</label>
                            <input type="number" className="form-control" id="seatsSold" placeholder="Enter Spectacle Seats Sold" name="seatsSold" value={spectacleFields.seatsSold} onChange={e => handleChangeSpectaclesField(e)} />
                        </div>
                    </>
                )}
                

                <button className="btn btn-primary" type="submit" onClick={e => handleSubmit(e)}>Update</button>
            </form>

            <div className="container d-flex justify-content-center">
                <button className="btn btn-primary" onClick={handleBack}>Back To Home</button>
            </div>
        </div>
    );
}

export default Edit;