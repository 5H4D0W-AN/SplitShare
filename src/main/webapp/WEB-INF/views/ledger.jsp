<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ledger with Vue.js</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input, select, textarea, button {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        textarea {
            resize: none;
        }
        .btn {
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            margin-top: 20px;
            padding: 10px;
        }
        .btn:disabled {
            background-color: #aaa;
            cursor: not-allowed;
        }
        .selected-list {
            list-style: none;
            padding: 0;
        }
        .selected-list li {
            margin: 5px 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .remove-btn {
            background-color: #ff0000; /* Red background */
            color: #fff; /* White icon color */
            border: none; /* No border */
            cursor: pointer; /* Pointer cursor on hover */
            padding: 0; /* No extra padding */
            border-radius: 50%; /* Circular button */
            font-size: 12px; /* Smaller icon size */
            display: flex;
            justify-content: center; /* Center the icon horizontally */
            align-items: center; /* Center the icon vertically */
            width: 20px; /* Small width */
            height: 20px; /* Small height */
            text-align: center; /* Align text */
        }
        .remove-btn i {
            font-size: 10px; /* Adjust icon size */
            margin: 0; /* No margin */
        }
        .remove-btn:hover {
            background-color: #cc0000;
        }
    </style>
</head>
<body>
    <div id="app" class="container">
        <h2>Ledger Entry</h2>
        <form id="ledgerForm" @submit.prevent="submitForm">
            <!-- Reason -->
            <div class="form-group">
                <label for="reason">Reason & Place:</label>
                <textarea v-model="reason" placeholder="Mention the reason and place of expenditure" rows="3" required></textarea>
            </div>

            <!-- Payers -->
            <div class="form-group">
                <label for="payer">Add Payer:</label>
                <select v-model="selectedPayer" :disabled="availablePayers.length === 0">
                    <option value="" disabled>Select a payer</option>
                    <option v-for="friend in availablePayers" :key="friend.id" :value="friend.id">
                        {{ friend.name }}
                    </option>
                </select>
                <button type="button" @click="addPayer" :disabled="!selectedPayer">Add Payer</button>
                <ul class="selected-list">
                    <li v-for="payer in payers" :key="payer.id">
                        {{ payer.name }}
                        <input type="number" v-model.number="payer.amount" placeholder="Enter amount" required />
                        <button type="button" class="remove-btn" @click="removePayer(payer.id)">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </li>
                </ul>
            </div>

            <!-- Participants -->
            <div class="form-group">
                <label for="participant">Add Non Paying Participants:</label>
                <select v-model="selectedParticipant" :disabled="availableParticipants.length === 0">
                    <option value="" disabled>Select a participant</option>
                    <option v-for="friend in availableParticipants" :key="friend.id" :value="friend.id">
                        {{ friend.name }}
                    </option>
                </select>
                <button type="button" @click="addParticipant" :disabled="!selectedParticipant">Add Participant</button>
                <ul class="selected-list">
                    <li v-for="participant in participants" :key="participant.id">
                        {{ participant.name }}
                        <button type="button" class="remove-btn" @click="removeParticipant(participant.id)">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </li>
                </ul>
            </div>

            <!-- Submit -->
            <button type="submit" class="btn" :disabled="payers.length === 0">Create Transactions</button>
        </form>
        <ul v-if ="transactions.length > 0">
            <h5> This Expense will Create: </h5>
            <li v-for="transaction in transactions" :key="transaction.id">
                        From: {{ transaction.fromAccount.id }} <br />
                        To: {{ transaction.toAccount.id }} <br />
                        By: {{ transaction.byAccount.id }} <br />
                        Reason: {{ transaction.reason }} <br />
                        Amount: {{ transaction.amount }} <br />
                        Date: {{ transaction.date }} <br />
                        <hr />
            </li>

        </ul>
        <div v-if="transactions.length > 0">
                <button class="btn btn-primary" @click="confirmTransactions">Confirm</button>
                <button class="btn btn-secondary" @click="cancelTransactions">Cancel</button>
        </div>

    </div>

    <div id = "app2">
        <h3>Pending Transactions</h3>

        <ul v-if ="pendingTransactions.length > 0">
                    <li v-for="transaction in pendingTransactions" :key="transaction.id">
                                <strong> From: </strong>{{ transaction.fromId }} <br />
                                <strong>To: </strong>{{ transaction.toId }} <br />
                                <strong>Created By: </strong>{{ transaction.byId }} <br />
                                <strong>Reason: </strong>{{ transaction.reason }} <br />
                                <strong>Amount: </strong>{{ transaction.amount }} <br />
                                <strong>Date: </strong>{{ transaction.date }} <br />
                                <strong>Status: </strong>{{transaction.status}} <br />
                                <hr />
                    </li>
        </ul>
        <p v-else>All Settled.</p>
    </div>


    <script>
        // Use server-side variables to initialize Vue.js app
        const friendsJson = ${friendsJson};
        const accountJson = ${accountJson};
        const pendingTransactionsJson = ${pendingTransactionsJson};

        new Vue({
            el: "#app",
            data: {
                reason: "",
                friends: [accountJson, ...friendsJson], // Friends data passed from the server
                account: accountJson,  // Current account data passed from the server
                payers: [],
                participants: [],
                selectedPayer: "",
                selectedParticipant: "",
                transactions: []
            },
            mounted() {
                        // Log friends data when the component is mounted
                        console.log("Friends data on mount:", this.friends);

            },
            computed: {
                availablePayers() {

                    if(!this.friends || !this.payers){
                        console.log("no data");
                        return [];
                    }
                    const available = this.friends.filter(friend =>
                        !this.payers.some(payer => payer.id === friend.id)
                    );
                    console.log("Filtered available payers:", available);
                    return available;
                },
                availableParticipants() {
                    if(!this.friends || !this.payers){
                         console.log("no data");
                         return [];
                    }
                    return this.friends.filter(friend =>
                        !this.participants.some(participant => participant.id === friend.id) &&
                        !this.payers.some(payer => payer.id === friend.id)
                    );
                }
            },
            methods: {
                addPayer() {
                    const selected = this.friends.find(friend => friend.id === this.selectedPayer);
                    if (selected) {
                        this.payers.push({ ...selected, amount: 0 });
                        this.selectedPayer = "";
                    }
                },
                removePayer(id) {
                    this.payers = this.payers.filter(payer => payer.id !== id);
                },
                addParticipant() {
                    const selected = this.friends.find(friend => friend.id === this.selectedParticipant);
                    if (selected) {
                        this.participants.push(selected);
                        this.selectedParticipant = "";
                    }
                },
                removeParticipant(id) {
                    this.participants = this.participants.filter(participant => participant.id !== id);
                },
                submitForm() {
                    const data = {
                        reason: this.reason,
                        payers: this.payers.map(payer => ({ id: payer.id, amount: payer.amount })),
                        participants: this.participants.map(participant => participant.id)
                    };
                    console.log(data); // Send this data to your controller via AJAX or form submission

                    fetch('/createTransactions',{
                        method: 'POST',
                        headers: {
                            'Content-Type' : 'application/json'
                        },
                        body : JSON.stringify(data)
                    })
                        .then(response => {
                            if(!response.ok){
                                console.log("Error creating Transactions");
                                throw new Error(`HTTP error! status: ${response.status}`);
                            }
                            return response.json();
                        })
                        .then(result =>{
                            console.log("Transactions created! Please, Confirm.");
                            this.transactions = result;

                        })
                        .catch(error=>{
                            console.error("Error creating transactions:", error);
                        });

                },

                confirmTransactions(){
                    fetch('/saveTransactions', {
                        method : 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(this.transactions)
                    })
                        .then(response => {
                            if(!response.ok){
                                console.error("Error saving Transactions.");
                                throw new Error(`HTTP error! status : ${response.status}`);
                            }
                            console.log("Transactions saved successfully,");
                            this.resetForm();
                        })
                        .catch(error=>{
                            console.error("Error saving Transactions.", error);
                        });
                },

                cancelTransactions(){
                    this.transactions = [];
                    this.resetForm();
                },

                resetForm(){
                    this.reason = "";
                    this.payers = [];
                    this.participants = [];
                    this.selectedPayer = "";
                    this.selectedParticipant = "";
                    this.transactions = [];
                },
            }
        });

        new Vue({
            el : '#app2',
            data : {
                pendingTransactions: pendingTransactionsJson
            }



        });
    </script>
</body>
</html>
