import { useEffect, useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import Table from 'react-bootstrap/Table';
import Trip from "./Trip";

function TripFactory({ trips, setTrips }) {

    const auth = useContext(AuthContext);
    
    useEffect(() => {
        getTrips();
    }, []);

    const getTrips = () => {
        fetch("http://localhost:8080/api/ride_on/trip", {
            headers: {
                Authorization: "Bearer " + auth.currentUser.token
            }
        })
       // .then(response => parseResponseMessage(response))
        .then(data => data ? setTrips(data) : null)
       // .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
    }

    const showTrips = () => {
        return trips.map(trip => <Trip key={trip.tripId + trip.arrival} trip={trip} />);
    }

    return (
        <Table>
            <thead>
                <tr>
                    <th scope="col">Departure</th>
                    <th scope="col">Arrival</th>
                    <th scope="col">Price per Seat</th>
                    <th scope="col">Date</th>
                    {auth.currentUser && auth.currentUser.hasRole("USER") ? (
                        <th scope="col">Actions</th>
                    ) : null }
                </tr>
            </thead>
            <tbody>
                {showTrips()}
            </tbody>
        </Table>
    );
}

export default TripFactory;