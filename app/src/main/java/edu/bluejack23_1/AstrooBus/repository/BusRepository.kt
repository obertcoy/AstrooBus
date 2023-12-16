package edu.bluejack23_1.AstrooBus.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.AstrooBus.database.FirebaseInitializer
import edu.bluejack23_1.AstrooBus.helper.AdapterHelper
import edu.bluejack23_1.AstrooBus.model.Bus

class BusRepository (){
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getAllBus( callback: (ArrayList<Bus>) -> Unit){
        val ref = db.collection("Bus").whereEqualTo("transactionId", "")

        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                val bus = documents?.map { document ->
                    val data = document.data
                    val busId = data?.get("busId") as String
                    val busPlateNumber = data?.get("busPlateNumber") as String
                    val busStatus = data?.get("busStatus") as String
                    val seatString = data?.get("seatString") as String
                    val seats = data?.get("seats") as Number
                    val transactionId = data?.get("transactionId") as String
                    Bus(busId, busPlateNumber, seats, busStatus, seatString, transactionId)
                }
                val busList = ArrayList(bus ?: emptyList())
                Log.d("TicketRepository", busList.size.toString())
                callback(busList)
            } else {
                // Handle query error
                Log.e("TicketRepository", "Firestore query failed", task.exception)
                callback(ArrayList())
            }
        }
    }

    fun addBus(bus:Bus, callback: (String) -> Unit) {
        val transaction = adapter.busToHashMap(bus)
        val ref = db.collection("Bus")

        ref.add(transaction)
            .addOnSuccessListener { documentReference ->
                Log.d("Bus Repository", "Success Adding Bus")
                val addAttr = hashMapOf(
                    "busId" to documentReference.id
                )
                db.collection("Bus")
                    .document(documentReference.id)
                    .update(addAttr as Map<String, Any>)
                    .addOnSuccessListener {
                        println("Document successfully updated!")
                        callback("success")
                    }
                    .addOnFailureListener { e ->
                        println("Error updating document: $e")
                    }
            }
            .addOnFailureListener { error ->
                Log.e("Bus Repository", "Fail Adding Bus", error)
            }
    }

    fun updateBusSeats(seatString: String, seatNumber: List<Int>, busId: String){
        val parts = seatString.split("/").toMutableList()
        for(seat in seatNumber){
            val row = (seat - 1) / 4 + 2
            var seats = parts[row]
            var seats1 = seats.get(0).toString()
            var seats2 = seats.get(1).toString()
            var seats3 = seats.get(3).toString()
            var seats4 = seats.get(4).toString()

            val space = seats.get(2)
            val inputttedSeats = (seat - 1) % 4
            if(inputttedSeats == 0){
                seats1 = "R"
            }else if (inputttedSeats == 1){
                seats2 = "R"
            }else if(inputttedSeats == 2){
                seats3 = "R"
            }else if(inputttedSeats == 3){
                seats4 = "R"
            }
            val string = seats1 + seats2 + space + seats3 + seats4
            parts[row] = string
        }
        val modifiedSeatString = "/" + parts[1] +"/" + parts[2]+"/" + parts[3]+"/" + parts[4]+"/" + parts[5]+"/" + parts[6]+"/" + parts[7]+"/" + parts[8]+"/" + parts[9]
        Log.d("BusRepository", "MODIFIED SEAT:" + modifiedSeatString)
        val newSeats = hashMapOf(
            "seatString" to modifiedSeatString
        )

        db.collection("Bus")
            .document(busId)
            .update(newSeats as Map<String, Any>)
            .addOnSuccessListener {
                println("Document successfully updated!")
            }
            .addOnFailureListener { e ->
                println("Error updating document: $e")
            }

    }

    fun updateBusStatus(busId: String, status:String){
        val newStatus = hashMapOf(
            "busStatus" to status
        )

        db.collection("Bus")
            .document(busId)
            .update(newStatus as Map<String, Any>)
            .addOnSuccessListener {
                println("Document successfully updated!")
            }
            .addOnFailureListener { e ->
                println("Error updating document: $e")
            }
    }

    fun getBus(busId: String, transactionId: String, callback: (Bus?) -> Unit) {
        val ref = db.collection("Bus")

        val query = ref.whereEqualTo("busId", busId).whereEqualTo("transactionId", transactionId)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                Log.d("BusRepository", busId + transactionId)
                if (documents != null && !documents.isEmpty()) {
                    val document = documents[0]
                    val data = document.data
                    val busId = data?.get("busId") as String
                    val busPlateNumber = data?.get("busPlateNumber") as String
                    val busStatus = data?.get("busStatus") as String
                    val seatString = data?.get("seatString") as String
                    val seats = data?.get("seats") as Number
                    val transactionId = data?.get("transactionId") as String

                    val bus = Bus(busId, busPlateNumber, seats, busStatus, seatString, transactionId)
                    Log.d("BusRepository", bus.seatString)
                    callback(bus)
                } else {
                    Log.d("BusRepository", "documents kosong")
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }

}