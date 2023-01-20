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

function TripForm() {
    
    // const [trip, setTrip] = useState(RIDE_ON_DEFAULT);
    // const [errors, setErrors] = useState([]);

    const auth = useContext(AuthContext);
    const { register } = useForm();

    const navigate = useNavigate();

    //const { id } = useParams();
    
    // useEffect(() => {
    //   if (id) {
    //     fetch("http://localhost:8080/api/ride_on")
    //     .then(response => {
    //       if (response.status === 200) {
    //         return response.json();
    //       } else {
    //         return Promise.reject(`Unexpected status code: ${response.status}`);
    //       }
    //     })
    //     .then(data => setTrip(data))
    //     .catch(console.log);
    //   }
    // }, [id]);

  return (
    <>
    <div className=''>
      <h1>Create a Trip</h1>
    </div>
    <form id='drive-form'>
        <div>
         <label className='form-label' type='text' id='departure' htmlFor='trip-departure'>Departure</label>
        <input className='form-control' type='text' id='trip-departure' {...register("departure", {required: "Must define a departure location"})}/>
        </div>

        <div>
        <label className='form-label' type='text' id='arrival' htmlFor='trip-arrival'>Arrival</label>
        <input className='form-control' type='text' id='trip-arrival' {...register("arrival", {required: "Must define an arrival location"})}/>
        </div>

        <div>
        <label className='form-label' type='number' id='seats' htmlFor='trip-seats'>Seats</label>
        <input className='form-control' type='number' id='trip-seats' {...register("seats", {required: "Must define a number of seats"})}/>
        </div>

        <div>
        <label className='form-label' type='number' id='price-per-seats' htmlFor='trip-price-per-seats'>Price Per Seat</label>
        <input className='form-control' type='number' id='trip-price-per-seats' {...register("price-per-seats", {required: "Must define a price per seat"})}/>
        </div>

        <div>
        <label className='form-label' type='date' id='date' htmlFor='trip-date'>Date</label>
        <input className='form-control' type='date' id='trip-date' {...register("date", {required: "Must define a date"})}/>
        </div>
        
        <div className='row offset-4'>
        <button className="btn btn-primary mt-3 col-3" type="submit">Submit</button>
        
            <button className="btn btn-secondary mt-3 ms-2 col-3" type="button" onClick={() => navigate("/home")}>Cancel</button> 
            </div>
    </form>
    </>
  )
}

export default TripForm;