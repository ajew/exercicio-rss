package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import br.ufpe.cin.if710.rss.R.string.rssfeed
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {

    //ao fazer envio da resolucao, use este link no seu codigo!
    private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"
    private var arrayAdapter = CustomArrayAdapter(emptyList(), this)
    //OUTROS LINKS PARA TESTAR...
    //http://rss.cnn.com/rss/edition.rss
    //http://pox.globo.com/rss/g1/brasil/
    //http://pox.globo.com/rss/g1/ciencia-e-saude/
    //http://pox.globo.com/rss/g1/tecnologia/

    //use ListView ao invés de TextView - deixe o atributo com o mesmo nome
    //private var conteudoRSS: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        conteudoRSS.adapter = arrayAdapter
    }

    override fun onStart() {
        super.onStart()
        try {
            carregarRSStask(getString(R.string.rssfeed))
            /*//Esse código dá pau, por fazer operação de rede na thread principal...
            val feedXML = getRssFeed(RSS_FEED)
            conteudoRSS!!.text = feedXML*/
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    fun carregarRSStask(url: String){
        doAsync {
            val xml = getRssFeed(url)
            val list = ParserRSS.parse(xml)

            uiThread {
                arrayAdapter.items = list
                arrayAdapter.notifyDataSetChanged()
            }
        }
    }
    //Opcional - pesquise outros meios de obter arquivos da internet - bibliotecas, etc.
    @Throws(IOException::class)
    private fun getRssFeed(feed: String): String {
        var inputStream: InputStream? = null
        var rssFeed = ""
        try {
            val url = URL(feed)
            val conn = url.openConnection() as HttpURLConnection
            inputStream = conn.getInputStream()
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count: Int = inputStream.read(buffer)
            while (count != -1) {
                out.write(buffer, 0, count)
                count = inputStream.read(buffer)
            }
            val response = out.toByteArray()
            rssFeed = String(response, charset("UTF-8"))
        } finally {
            if(inputStream != null) {
                inputStream.close()
            }
        }
        return rssFeed
    }
}

