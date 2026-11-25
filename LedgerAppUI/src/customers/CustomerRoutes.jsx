import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "../ProtectedRoute.jsx";
import CustomerDashboard from "./CustomerDashboard.jsx";
import FundTransferFromSelfPage from "./FundTransferFromSelfPage.jsx"
import Deposit from "./Deposit.jsx";
import Withdraw from "./Withdraw.jsx";
const CustomerRoutes = () => {
  return (
    <Routes>
      <Route element={<ProtectedRoute allowedRoles={["ROLE_CUSTOMER"]} />}>
        <Route path="dashboard" element={<CustomerDashboard />} />
        <Route path="self-fund-transfer" element={<FundTransferFromSelfPage />} />
        <Route path="self-deposit" element={<Deposit />} />
        <Route path="self-withdraw" element={<Withdraw />} />
      </Route>
    </Routes>
  );
};
export default CustomerRoutes;
