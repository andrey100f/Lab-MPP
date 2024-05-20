import { useState } from "react";

import { toast } from "react-toastify";

import List from "./List";

import { addInitialSpectacle } from "../utils/props";
import { addSpectacle } from "../utils/api";

function Home() {
    const [spectacleFields, setSpectacleFields] = useState(addInitialSpectacle);
    const [loading, setLoading] = useState(false); 

    function handleChangeSpectaclesField(e : React.ChangeEvent<HTMLInputElement>) {
        setSpectacleFields({
            ...spectacleFields,
            [e.target.name]: e.target.value
        });
    }

    async function handleSubmit(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        e.preventDefault();

        try {
            await addSpectacle(spectacleFields);
            toast.success("Spectacle added successfully");
        }
        catch (err) {
            toast.error("Error adding spectacle");
        }
        
        setLoading(true);
    }

    if(loading) {
        return <Home />
    }

    return (
        <div className="container">
            <h2 className="w-100 d-flex justify-content-center p-3">ConcertPro Spectacles CRUD</h2>

            <div className="row">
                <div className="col-md-3">
                    <div className="mb-3 mt-3">
                        <label htmlFor="artistName" className="form-label">Artist Name</label>
                        <input type="text" className="form-control" id="artistName" placeholder="Enter Artist Name" name="artistName" onChange={e => handleChangeSpectaclesField(e)} />
                    </div>

                    <div className="mb-3 mt-3">
                        <label htmlFor="spectacleDate" className="form-label">Spectacle Date</label>
                        <input type="date" className="form-control" id="spectacleDate" name="spectacleDate" onChange={e => handleChangeSpectaclesField(e)} />
                    </div>

                    <div className="mb-3 mt-3">
                        <label htmlFor="spectaclePlace" className="form-label">Spectacle Place</label>
                        <input type="text" className="form-control" id="spectaclePlace" placeholder="Enter Spectacle Place" name="spectaclePlace" onChange={e => handleChangeSpectaclesField(e)} />
                    </div>

                    <div className="mb-3 mt-3">
                        <label htmlFor="seatsAvailable" className="form-label">Spectacle Seats Available</label>
                        <input type="number" className="form-control" id="seatsAvailable" placeholder="Enter Spectacle Seats Available" name="seatsAvailable" onChange={e => handleChangeSpectaclesField(e)} />
                    </div>

                    <div className="mb-3 mt-3">
                        <label htmlFor="seatsSold" className="form-label">Spectacle Seats Sold</label>
                        <input type="number" className="form-control" id="seatsSold" placeholder="Enter Spectacle Seats Sold" name="seatsSold" onChange={e => handleChangeSpectaclesField(e)} />
                    </div>

                    <button className="btn btn-primary" type="submit" onClick={e => handleSubmit(e)}>Add Spectacle</button>
                </div>

                <div className="col-md-9">
                    <List />
                </div>
            </div>
        </div>
    )
}

export default Home;