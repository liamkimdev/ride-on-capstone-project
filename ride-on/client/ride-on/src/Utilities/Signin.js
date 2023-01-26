import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { useForm } from "react-hook-form";

function Signin({ messages, setMessages, makeId, isPasswordComplex }) {

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
    rootElement.style.height = "100vh";
  }

  useEffect(() => {
    changeBackground();
  }, []);

  const onSubmit = (userData) => {
    fetch("http://localhost:8080/api/ride_on/user/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    })
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else if (response.status === 403) {
          setMessages([
            ...messages,
            {
              id: makeId(),
              type: "failure",
              text: "Account could not be logged in.",
            },
          ]);
        } else {
          setMessages([
            ...messages,
            {
              id: makeId(),
              type: "failure",
              text: "An unexpected error occurred.",
            },
          ]);
          navigate("/signup");
        };
      })
      .then((data) => {
        console.log(data);
        auth.login(data.jwt_token);
        navigate("/transport");
        setMessages([
          ...messages,
          {
            id: makeId(),
            type: "success",
            text: "Welcome to Ride On!",
          },
        ]);
      })
      .catch((error) => console.log(error));
  };

  return (
    <div className="container pt-5 mt-5 cb-pale">
      <div className="row">
        <div className="col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3">
          <div className="bubble-box text slide-right">
            <div className="text-center">
              <h3>Sign In</h3>
            </div>

            <form id="login-form" onSubmit={handleSubmit(onSubmit)}>

              <label className="form-label mt-3" htmlFor="user-email">
                Email
              </label>
              <input
                className="form-control col-3"
                type="email"
                id="user-email"
                placeholder="Email"
                {...register("username", {
                  required: "Must have a valid email address",
                  maxLength: {
                    value: 50,
                    message: "Must be 50 characters or less.",
                  },
                  pattern: {
                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    message:
                      "Invalid characters, please input a valid email address.",
                  },
                })}
              />

              <p className="form-errors">{errors.username?.message}</p>

              <label className="form-label mt-3" htmlFor="user-password">
                Password
              </label>
              <input
                className="form-control"
                type="password"
                id="user-password"
                placeholder="Password"
                {...register("password", {
                  required: "Must have a valid password",
                  minLength: {
                    value: 8,
                    message: "Must have at least 8 characters.",
                  },
                  validate: (v) =>
                    isPasswordComplex(v) ||
                    "Must contain at least one of the following: an uppercase letter, a lowercase letter, a number, and a special character.",
                })}
              />
              <p className="form-errors">{errors.password?.message}</p>

              <div className="text-center">
                <button className="btn mt-3" type="submit"
                  style={{ color: "#FFFFFF", backgroundColor: "#3CB2FB" }}>
                  Sign In
                </button>
                <button
                  className="btn mt-3 ms-2"
                  type="button"
                  style={{ color: "#FFFFFF", backgroundColor: "#FF4571" }}
                  onClick={() => navigate("/transport")}
                >
                  Cancel
                </button>

                <div className="m-4">
                  <p>Not a member? <a href="#!" onClick={() => navigate("/signup")}>Sign Up</a></p>
                </div>
              </div>

            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Signin;
