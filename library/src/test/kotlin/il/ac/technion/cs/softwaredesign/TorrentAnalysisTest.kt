package il.ac.technion.cs.softwaredesign
import com.natpryce.hamkrest.allElements
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset


class TorrentAnalysisTest {
    private val debian = this::class.java.getResource("/debian-10.3.0-amd64-netinst.iso.torrent").readText(Charset.forName("ISO-8859-1"))
    private val superhuman =  this::class.java.getResource("/superhuman by habit epub.torrent").readText(Charset.forName("ISO-8859-1"))

    @Test
    fun `getInfoHash calculated correctly for debian`() {
        val infohash = getInfoHash(debian)
        assertThat(infohash, equalTo("5a8062c076fa85e8056451c0d9aa04349ae27909"))
    }

    @Test
    fun `getInfoHash calculated correctly for superhuman`() {
        val infohash = getInfoHash(superhuman)
        assertThat(infohash, equalTo("627566b90d81958a75847622477f1a699f1b2907"))
    }




}