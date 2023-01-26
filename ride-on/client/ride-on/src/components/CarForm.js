import React from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useState, useContext, useEffect } from "react";
import AuthContext from "../contexts/AuthContext";

function CarForm({
  messages,
  setMessages,
  makeId,
  cars,
  setCars,
  addCarToCurrentUser,
}) {
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm();

  const auth = useContext(AuthContext);

  const navigate = useNavigate();

  const changeBackground = () => {
    const rootElement = document.getElementById("root");
    rootElement.style.background = `url(${process.env.PUBLIC_URL + '/images/jeep.gif'}) repeat-y center fixed`;
    rootElement.style.backgroundSize = "cover";
    rootElement.style.height = "100%";
  }

  useEffect(() => {
    changeBackground();
  }, []);

  const onSubmit = (carData) => {
    let reviseCarData = {
      ...carData,
      userId: auth.currentUser.userId,
    };

    fetch("http://localhost:8080/api/ride_on/car", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + auth.currentUser.token,
      },
      body: JSON.stringify(reviseCarData),
    })
      .then((response) => {
        if (response.status === 201) {
          navigate("/transport")
          setMessages([
            ...messages,
            {
              id: makeId(),
              type: "success",
              text: "Car successfully created.",
            },
          ]);
          return response.json();
        } else if (response.status === 403) {
          setMessages([
            ...messages,
            {
              id: makeId(),
              type: "failure",
              text: "Car could not be created.",
            },
          ]);
          navigate("/api/ride_on/car/form");
        } else {
          setMessages([
            ...messages,
            {
              id: makeId(),
              type: "failure",
              text: "An unexpected error occurred.",
            },
          ]);
          navigate("/");
        }
      })
      .then((data) => {
        addCarToCurrentUser(data);
        setCars([...cars, data]);
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <div className="container pt-5 mt-5">
        <div className="col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3 bubble-box text slide-right">
          <div className='text-center'>
            <h1>Create a Car</h1>
          </div>
          <form id="car-form" onSubmit={handleSubmit(onSubmit)}>
            <div>
              <label
                className="form-label"
                type="text"
                id="make"
                htmlFor="car-make"
              >
                Make
              </label>
              <input
                className="form-control"
                type="text"
                id="car-make"
                placeholder="Make"
                {...register("make", { required: "Must define a car make" })}
              />
              <p className="form-errors">{errors.make?.message}</p>
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
                placeholder="Model"
                {...register("model", { required: "Must define a car model" })}
              />
              <p className="form-errors">{errors.model?.message}</p>
            </div>

            <div>
              <label
                className="form-label"
                type="text"
                id="year"
                htmlFor="car-year"
              >
                Year
              </label>
              <input
                className="form-control"
                type="text"
                id="car-year"
                placeholder="Year (Optional)"
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
                placeholder="Color"
                {...register("color", { required: "Must define a car color" })}
              />
              <p className="form-errors">{errors.color?.message}</p>
            </div>

            <div>
              <label
                className="form-label"
                type="text"
                id="license-plate"
                htmlFor="car-license-plate"
              >
                License Plate No.
              </label>
              <input
                className="form-control"
                type="text"
                id="car-license-plate"
                placeholder="License Plate No."
                {...register("licensePlate", {
                  required: "Must define a car license plate",
                })}
              />
              <p className="form-errors">{errors.licensePlate?.message}</p>
            </div>

            <div>
              <input
                type="checkbox"
                value="true"
                name="insurance"
                {...register("insurance", { required: "Must have an insurance" })}
              />{" "}
              Insurance
              <p className="form-errors">{errors.insurance?.message}</p>
              <br />
              <input
                type="checkbox"
                value="true"
                name="registration"
                {...register("registration", {
                  required: "Must have a registration",
                })}
              />{" "}
              Registration
              <p className="form-errors">{errors.registration?.message}</p>
            </div>

            <div className="row offset-4">
              <button className="btn mt-3 col-3" type="submit"
                style={{ color: "#FFFFFF", backgroundColor: "#3CB2FB" }}
                onClick={onSubmit}
              >
                Submit
              </button>

              <button
                className="btn mt-3 ms-2 col-3"
                type="button"
                style={{ color: "#FFFFFF", backgroundColor: "#FF4571" }}
                onClick={() => navigate("/transport")}
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}

export default CarForm;
