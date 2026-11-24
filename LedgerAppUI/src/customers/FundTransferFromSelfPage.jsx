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
import AdminHeader from "./CustomerHeader"
import useAuth from "../AuthContext.jsx";
export default function TransferPage() {
    const { loginUser } = useAuth();
    const [receiver, setReceiver] = useState("");
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
        if (!receiver || !amount || Number(amount) <= 0) return;

        var res;
        var message = `Transferred ₹${Number(amount).toLocaleString("en-IN")} to ${receiver}`;
        const body = {
            receiver: receiver,
            amount: amount
        }
        try {
            res = await HttpClient.post("/ledger/transfer/self-transfer", body)
            setReceiver("");
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
                        <CardTitle>Fund Transfer</CardTitle>
                        <CardDescription>
                            Enter receiver, and amount details to initiate a payment.
                        </CardDescription>
                    </CardHeader>

                    <form onSubmit={handleTransfer}>
                        <CardContent className="space-y-4">


                            <div className="space-y-2">
                                <Label htmlFor="receiver">Receiver (Account UUID)</Label>
                                <Input
                                    id="receiver"
                                    type="text"
                                    placeholder="Account UUID of the receiver"
                                    value={receiver}
                                    onChange={(e) => setReceiver(e.target.value)}
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
                                Transfer
                            </Button>
                        </CardFooter>
                    </form>
                </Card>
            </div>
        </>);
}
