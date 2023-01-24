import { useEffect, useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import Table from 'react-bootstrap/Table';
import Trip from "./Trip";

function TripFactory({ trips, setTrips, currentUser, cars }) {

    const auth = useContext(AuthContext);

    useEffect(() => {
        getTrips();
    }, [trips]);

    const getTrips = () => {
        fetch("http://localhost:8080/api/ride_on/trip")
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                } else if (response.status === 403) {
                    // setMessages([
                    //   ...messages,
                    //   {
                    //     id: makeId(),
                    //     type: "failure",
                    //     text: "Car could not be registered.",
                    //   },
                    // ]);
                    console.log(response);
                } else {
                    // setMessages([
                    //     ...messages,
                    //     {
                    //       id: makeId(),
                    //       type: "failure",
                    //       text: "Unexpected error occured.",
                    //     },
                    //   ]);
                    console.log(response);
                };
            })
            .then(data => {
                if (data) {
                    setTrips(data);
                }
            })
            // .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
            .catch((error) => console.log(error));
    }

    const showTrips = () => {
        return trips.map(trip => <Trip key={trip.tripId} trip={trip} currentUser= {currentUser} cars = {cars} trips={trips}/>);
    }

    return (
        <div className="bubble-box text slide-right">
        <Table>
            <thead>
                <tr>
                    <th scope="col">Departure</th>
                    <th scope="col">Arrival</th>
                    <th scope="col">Seats</th>
                    <th scope="col">Price per Seat</th>
                    <th scope="col">Date</th>
                    {auth.currentUser && auth.currentUser.hasRole("USER") ? (
                        <th scope="col">Actions</th>
                    ) : null}
                </tr>
            </thead>
            <tbody>
                {showTrips()}
            </tbody>
        </Table>
        </div>
    );
}

export default TripFactory;