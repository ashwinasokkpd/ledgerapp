import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "../ProtectedRoute.jsx";
import CustomerDashboard from "./CustomerDashboard.jsx";
import FundTransferFromSelfPage from "./FundTransferFromSelfPage.jsx"
const CustomerRoutes = () => {
  return (
    <Routes>
      <Route element={<ProtectedRoute allowedRoles={["ROLE_CUSTOMER"]} />}>
        <Route path="dashboard" element={<CustomerDashboard />} />
        <Route path="self-fund-transfer" element={<FundTransferFromSelfPage />} />
      </Route>
    </Routes>
  );
};
export default CustomerRoutes;
