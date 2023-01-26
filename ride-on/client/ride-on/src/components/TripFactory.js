import { useEffect, useContext, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import Table from 'react-bootstrap/Table';
import Trip from "./Trip";

function TripFactory({ currentUser, messages, setMessages, makeId }) {

    const [trips, setTrips] = useState([]);

    const auth = useContext(AuthContext);

    useEffect(() => {
        getTrips();
    }, []);


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

    const deleteTrips = (tripId) => {
        setTrips(currentList => [...currentList].filter(tr => tr.tripId != tripId));
    }
    const showTrips = () => {
        return trips.map(trip => <Trip key={trip.tripId} trip={trip} currentUser={currentUser} updateTrip={updateTrip} deleteTrips={deleteTrips} messages={messages} setMessages={setMessages} makeId={makeId} />);
    }


    const updateTrip = (tripId, rider) => {
        const index = trips.findIndex(tr => tr.tripId == tripId);
        const tripsCopy = [...trips];
        const tripCopy = { ...tripsCopy[index], riders: [...tripsCopy[index].riders, rider] };
        tripsCopy.splice(index, 1, tripCopy);
        setTrips(tripsCopy);
    }

    return (
        <div className="container pt-5 mt-5">
            <div className="bubble-box text slide-right text-center">
                <h1>Trips</h1>
                <hr></hr>
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
        </div>
    );
}

export default TripFactory;