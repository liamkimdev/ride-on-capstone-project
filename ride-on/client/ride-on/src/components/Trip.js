import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Trip ({ trip }) {

    const auth = useContext(AuthContext);

    const navigate = useNavigate();

    return (
        <tr>
            <td>{trip.departure}</td>
            <td>{trip.arrival}</td>
            <td>{trip.pricePerSeat}</td>
            <td>{trip.date}</td>
            {auth.currentUser && auth.currentUser.hasRole("USER") ? (
                <td>
                    <button className="btn btn-secondary" onClick={() => navigate("/api/ride_on/trip" + trip.tripId)}>Join</button>
                    <button className="btn btn-danger ms-2" onClick={() => navigate("/api/ride_on/trip" + trip.tripId)}>Cancel</button>
                </td>
            ) : null }
        </tr>
    );
}

export default Trip;