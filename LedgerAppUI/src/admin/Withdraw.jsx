"use client";

import React, { useState, useEffect } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import HttpClient from "../httpClient";
import AdminHeader from "./AdminHeader"
export default function Withdraw() {
  const [withdrawer, setwithdrawer] = useState("");
  const [amount, setAmount] = useState("");
  const [status, setStatus] = useState(null);

  // Automatically clear the status message after 4 seconds
  useEffect(() => {
    if (!status) return;

    const timer = setTimeout(() => {
      setStatus(null);
    }, 4000);

    // Cleanup timer if status changes early or component unmounts
    return () => clearTimeout(timer);
  }, [status]);

  const handleTransfer = async (e) => {
    e.preventDefault();
    if (!withdrawer || !amount || Number(amount) <= 0) return;

    var res;
    var message = `Deposited ₹${Number(amount).toLocaleString("en-IN")} to ${withdrawer}`;
    const body = {
      depositorId: withdrawer,
      amount: amount
    }
    try {
      res = await HttpClient.post("/ledger/transfer/withdraw", body)
      setwithdrawer("");
    } catch (e) {
      message = "Transfer failed.\n" + JSON.stringify(e.response?.data) || e.message;
      console.log(e);
    }
    setStatus(
      message
    );
    setAmount("");
  };

  return (
    <><AdminHeader />
      <div className="flex min-h-screen items-center justify-center bg-slate-50 p-4">
        <Card className="w-full max-w-md">
          <CardHeader>
            <CardTitle>Withdraw money from withdrawer account</CardTitle>
            <CardDescription>
              Enter withdrawer uuid and amount details to initiate a withdraw.
            </CardDescription>
          </CardHeader>

          <form onSubmit={handleTransfer}>
            <CardContent className="space-y-4">

              <div className="space-y-2">
                <Label htmlFor="withdrawer">withdrawer (Account UUID)</Label>
                <Input
                  id="withdrawer"
                  type="text"
                  placeholder="Account UUID of the withdrawer"
                  value={withdrawer}
                  onChange={(e) => setwithdrawer(e.target.value)}
                  required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="amount">Amount (₹)</Label>
                <Input
                  id="amount"
                  type="number"
                  step="0.01"
                  min="0.01"
                  placeholder="0.00"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  required
                />
              </div>

              {status && (
                <p className="rounded-md border border-emerald-200 bg-emerald-50 p-3 text-sm text-emerald-700">
                  {status}
                </p>
              )}
            </CardContent>

            <CardFooter>
              <Button type="submit" className="w-full">
                Withdraw
              </Button>
            </CardFooter>
          </form>
        </Card>
      </div>
    </>);
}
