import { useContext } from "react";
import { Link, NavLink } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Nav() {
  const auth = useContext(AuthContext);

  return (
    <nav className="centero navbar navbar-expand-md navbar-light bg-white fixed-top sticky-top mb-5">
    <Link className="navbar-brand" to="/">
      Ride On
    </Link>
    <button
      className="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarNavDropdown"
      aria-controls="navbarNavDropdown"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span className="navbar-toggler-icon"></span>
    </button>
    <div className="collapse navbar-collapse" id="navbarNavDropdown">
      <ul className="navbar-nav">
        <li className="nav-item">
          <NavLink
            to="/home"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            Home
          </NavLink>
        </li>
        <li className="nav-item">
          <NavLink
            to="/about"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            About
          </NavLink>
        </li>
      
        {!auth.currentUser ? (
          <>
            <li className="nav-item">
              <NavLink
                to="/login"
                className={({ isActive }) =>
                  isActive ? "nav-link active" : "nav-link"
                }
              >
                Login
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                to="/register"
                className={({ isActive }) =>
                  isActive ? "nav-link active" : "nav-link"
                }
              >
                Register
              </NavLink>
            </li>
          </>
        ) : null}
        {auth.currentUser ? (
          <li>
            <Link className="nav-link" onClick={auth.logout}>
              Logout
            </Link>
          </li>
        ) : null}
      </ul>
    </div>
  </nav>
);
};
    // <nav className="mt-3 navbar navbar-expand-md navbar-light bg-white fixed-top sticky-top  mb-5 ">
    //   <div className="container-fluid">
    //     <Link className="navbar-brand" to="/">
    //       Ride On
    //     </Link>


    //     <ul className="navbar-nav me-auto mb-2 mb-md-0">
    //       <li className="nav-item">
    //         <NavLink
    //           to="/home"
    //           className={({ isActive }) =>
    //             isActive ? "nav-link active" : "nav-link"
    //           }
    //         >
    //           Home
    //         </NavLink>
    //       </li>
    //       <li className="nav-item">
    //         <NavLink
    //           to="/about"
    //           className={({ isActive }) =>
    //             isActive ? "nav-link active" : "nav-link"
    //           }
    //         >
    //           About
    //         </NavLink>
    //       </li>
    //       {auth.currentUser ? (
    //         <li className="nav-item">
    //           <NavLink
    //             to="/drive"
    //             className={({ isActive }) =>
    //               isActive ? "nav-link active" : "nav-link"
    //             }
    //           >
    //             Drive
    //           </NavLink>
    //         </li>
    //       ) : null}

    //       {!auth.currentUser ? (
    //         <>
    //           <li className="nav-item">
    //             < NavLink
    //               to="/login"
    //               className={({ isActive }) =>
    //                 isActive ? "nav-link active" : "nav-link"
    //               }
    //             >
    //               Login
    //             </NavLink>
    //           </li>
    //           <li className="nav-item">
    //             <NavLink
    //               to="/register"
    //               className={({ isActive }) =>
    //                 isActive ? "nav-link active" : "nav-link"
    //               }
    //             >
    //               Register
    //             </NavLink>
    //           </li>
    //         </>
    //       ) : null}
    //       {auth.currentUser ? (
    //         <li>
    //           <Link className="nav-link" onClick={auth.logout}>
    //             Logout
    //           </Link>
    //         </li>
    //       ) : null}
    //     </ul>
    //   </div>
    // </nav>


export default Nav;
