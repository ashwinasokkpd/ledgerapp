import { useState } from "react";
import { Link } from "react-router-dom";
import useAuth from "../AuthContext.jsx";
import HttpClient from "../httpClient.js";
import CustomerHeader from "./CustomerHeader.jsx";
const CustomerDashboard = () => {
  const { logout, loginUser } = useAuth();
  const [data, setData] = useState("");
  const [count, setCount] = useState(0);

  const handleGetTest = async () => {
    const response = await HttpClient.get("/admin/test");
    setData(response.data);
    return;
  };
  const handlePostTest = async () => {
    await HttpClient.post("/admin/test", { id: count });
    setCount(() => count + 1);
    return;
  };
  return (
    <>
      <CustomerHeader />
      <div className="flex items-center justify-center min-h-[80vh]">
        <div className="flex flex-col items-center justify-center min-h-[80vh]">
          <h1 className="text-3xl font-bold">Welcome {loginUser.username}</h1><br />
          <h2 className="text-lg text-gray-600"> [{loginUser.roles}][{loginUser.id}]</h2>
        </div >
      </div>
    </>
  );
};
export default CustomerDashboard;
