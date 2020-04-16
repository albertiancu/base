package il.ac.technion.cs.softwaredesign

import com.natpryce.hamkrest.allElements
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import il.ac.technion.cs.softwaredesign.storage.read
import il.ac.technion.cs.softwaredesign.storage.write
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import kotlin.reflect.jvm.internal.impl.storage.StorageKt


class DBCommunicationTest {

    @Test
    fun `writeToDB writes to the database`() {
        val mockDB = HashMap<String,String>()
        val keySlot = slot<ByteArray>()
        val valueSlot = slot<ByteArray>()

        mockkStatic("il.ac.technion.cs.softwaredesign.storage.SecureStorageKt")
        every {write(capture(keySlot), capture(valueSlot))} answers
                {mockDB.put(String(keySlot.captured), String(valueSlot.captured))}


        writeToDB("Key", "Value")

        assertThat(mockDB["Key"],equalTo( "Value"))
    }


    @Test
    fun `key exists in DB after writing a value to it`(){
        val mockDB = HashMap<String,String>()
        val keySlot = slot<ByteArray>()
        val valueSlot = slot<ByteArray>()
        val readKeySlot = slot<ByteArray>()
        mockkStatic("il.ac.technion.cs.softwaredesign.storage.SecureStorageKt")
        every {write(capture(keySlot), capture(valueSlot))} answers
                {mockDB.put(String(keySlot.captured), String(valueSlot.captured))}

        writeToDB("Key", "Value")

        //Guessing that using the ?. operator here if the get fu returns null then the whole scope returns null?
        every {read(capture(readKeySlot))} answers
                {
                    mockDB.get(String(readKeySlot.captured))?.toByteArray()}

        assertThat(keyExists("Key"), equalTo(true))
    }

    @Test
    fun `key doesn't exist in DB after insering and deleting it`(){
        val mockDB = HashMap<String,String>()
        val keySlot = slot<ByteArray>()
        val valueSlot = slot<ByteArray>()
        val readKeySlot = slot<ByteArray>()
        mockkStatic("il.ac.technion.cs.softwaredesign.storage.SecureStorageKt")
        every {write(capture(keySlot), capture(valueSlot))} answers
                {mockDB.put(String(keySlot.captured), String(valueSlot.captured))}

        writeToDB("Key", "Value")
        eraseFromDB("Key")

        //Guessing that using the ?. operator here if the get fu returns null then the whole scope returns null?
        every {read(capture(readKeySlot))} answers
                {
                    mockDB.get(String(readKeySlot.captured))?.toByteArray()}

        assertThat(keyExists("Key"), equalTo(false))
    }

}