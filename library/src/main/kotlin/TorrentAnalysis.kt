package il.ac.technion.cs.softwaredesign

import be.adaxisoft.bencode.BDecoder
import be.adaxisoft.bencode.BEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}
fun getInfoHash(torrent: String): String {
    var b = BDecoder(torrent.byteInputStream(Charset.forName("ISO-8859-1")))
    var document = b.decodeMap();
    val info = document.map["info"]!!.map
    val bb = BEncoder.encode(info)
    val bytes = ByteArray(bb.remaining())
    bb.get(bytes)

    val digest = MessageDigest.getInstance("SHA-1")
    digest.reset()
    val infoHASHH = digest.digest(bytes)
    val infohash = infoHASHH.toHex()
    return infohash
}
