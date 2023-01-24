import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom';
import AuthContext from '../contexts/AuthContext';
import { useForm } from 'react-hook-form';

// const RIDE_ON_DEFAULT = {
//     departure: '',
//     arrival: '',
//     seats: 0,
//     pricePerSeat: 0,
//     date: ''
// };

function TripForm( { currentUser, setCurrentUser } ) {

  // const [trip, setTrip] = useState(RIDE_ON_DEFAULT);
  // const [errors, setErrors] = useState([]);
  const { handleSubmit,
    register,
    formState: { errors },
  } = useForm();

  const auth = useContext(AuthContext);

  const navigate = useNavigate();

  const onSubmit = (tripData) => {
    
    console.log(tripData);

    let reviseTripData = {
      ...tripData,
      carId: auth.currentUser.cars[0].carId,
      // we need to register the car ID
    }

    console.log(reviseTripData);

    fetch("http://localhost:8080/api/ride_on/trip", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + auth.currentUser.token
      },
      body: JSON.stringify(reviseTripData),
    })
      .then((response) => {
        if (response.status === 201) {
          //setCurrentUser(...currentUser); TODO: we want to refresh the state of the current user
          navigate("/transport")
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
          navigate("/api/ride_on/trip/form");
        } else {
          // setMessages([
          //     ...messages,
          //     {
          //       id: makeId(),
          //       type: "failure",
          //       text: "Unexpected error occured.",
          //     },
          //   ]);
          navigate("/api/ride_on/trip/form");
        };
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <div className=''>
        <h1>Create a Trip</h1>
      </div>
      <form id='drive-form' onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label className='form-label' type='text' id='departure' htmlFor='trip-departure'>Departure</label>
          <input className='form-control' type='text' id='trip-departure' {...register("departure", { required: "Must define a departure location" })} />
          <p className="form-errors">
            { errors.departure?.message }
          </p>
        </div>

        <div>
          <label className='form-label' type='text' id='arrival' htmlFor='trip-arrival'>Arrival</label>
          <input className='form-control' type='text' id='trip-arrival' {...register("arrival", { required: "Must define an arrival location" })} />
          <p className="form-errors">
            { errors.arrival?.message }
          </p>
        </div>

        <div>
          <label className='form-label' type='number' id='seats' htmlFor='trip-seats'>Seats</label>
          <input className='form-control' type='number' id='trip-seats' {...register("seats", { required: "Must define a number of seats" })} />
          <p className="form-errors">
            { errors.seats?.message }
          </p>
        </div>

        <div>
          <label className='form-label' type='number' id='price-per-seats' htmlFor='trip-price-per-seats'>Price Per Seat</label>
          <input className='form-control' type='number' id='trip-price-per-seats' {...register("pricePerSeat", { required: "Must define a price per seat" })} />
          <p className="form-errors">
            { errors.pricePerSeat?.message }
          </p>
        </div>

        <div>
          <label className='form-label' type='date' id='date' htmlFor='trip-date'>Date</label>
          <input className='form-control' type='date' id='trip-date' {...register("date", { required: "Must define a date" })} />
          <p className="form-errors">
            { errors.date?.message }
          </p>
        </div>

        <div className='row offset-4'>
          <button className="btn btn-primary mt-3 col-3" type="submit">Submit</button>

          <button className="btn btn-secondary mt-3 ms-2 col-3" type="button" onClick={() => navigate("/transport")}>Cancel</button>
        </div>
      </form>
    </>
  );
}

export default TripForm;