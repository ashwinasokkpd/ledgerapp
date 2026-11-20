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
        <h2>Welcome [customer]</h2>
      </div>
    </>
  );
};
export default CustomerDashboard;
