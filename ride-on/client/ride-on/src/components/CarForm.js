import React from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from 'react-router-dom';


function CarForm() {
  const { register } = useForm();
    const navigate = useNavigate();

  return (
    // insurance
    // registration
<>
<h1>Create a Car</h1>
    <form>
      <div>
        <label className="form-label" type="text" id="make" htmlFor="car-make">
          Make
        </label>
        <input
          className="form-control"
          type="text"
          id="car-make"
          {...register("make", { required: "Must define a car make" })}
        />
      </div>

      <div>
        <label
          className="form-label"
          type="text"
          id="model"
          htmlFor="car-model"
        >
          Model
        </label>
        <input
          className="form-control"
          type="text"
          id="car-model"
          {...register("model", { required: "Must define a car model" })}
        />
      </div>

      <div>
        <label className="form-label" type="text" id="year" htmlFor="car-year">
          Year
        </label>
        <input
          className="form-control"
          type="text"
          id="car-year"
          {...register("year")}
        />
      </div>

      <div>
        <label
          className="form-label"
          type="text"
          id="color"
          htmlFor="car-color"
        >
          Color
        </label>
        <input
          className="form-control"
          type="text"
          id="car-color"
          {...register("color", { required: "Must define a car color" })}
        />
      </div>

      <div>
        <label
          className="form-label"
          type="text"
          id="license-plate"
          htmlFor="car-lisence-plate"
        >
          Lisence Plate
        </label>
        <input
          className="form-control"
          type="text"
          id="car-license-plate"
          {...register("lisencePlate", {
            required: "Must define a car license plate",
          })}
        />
      </div>

      <div>
        <input type="checkbox" value="true" name="insurance" /> Insurance
        <input type="checkbox" value="true" name="registration" /> Registration
      </div>

      <div className='row offset-4'>
        <button className="btn btn-primary mt-3 col-3" type="submit">Submit</button>
        
            <button className="btn btn-secondary mt-3 ms-2 col-3" type="button" onClick={() => navigate("/home")}>Cancel</button> 
            </div>
    </form>
    </>
  );
}

export default CarForm;
