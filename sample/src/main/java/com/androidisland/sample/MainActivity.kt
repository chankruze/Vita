package com.androidisland.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.androidisland.vita.Vita
import com.androidisland.vita.VitaOwner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val vm by Vita.with(VitaOwner.Single(this)).viewModel() {
        VitaVM("lazy vita!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lc = lifecycle
        Log.d("test1", "-->${lc::class.java}")

        val adapter = Adapter(getString(R.string.raw))
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(this, VERTICAL))


        val store = (application as App).store
        val provider = ViewModelProvider(
            store,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
//        val vm = provider[VitaVM::class.java]
//        Log.d("test1", "store-> $store")
//        Log.d("test1", "provider-> $provider")
//        Log.d("test1", "vm-> $vm")
//        vm.live.observe(this, Observer {
//            text.text = it
//            Log.d("test1", "live-> $it")
//        })
//        vm.setData("Hello world!")

        text.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        val vm2 = Vita
            .with(VitaOwner.None)
            .getViewModel() {
                VitaVM("Vita global!")
            }

        val vm3 = Vita
            .with(VitaOwner.None)
            .getViewModel<ViewModelNoFactory>()

        val vm4 = Vita
            .with(VitaOwner.None)
            .getViewModel<ViewModelNoFactory>()

        Log.d("test123", "vM2=>$vm2")
        Log.d("test123", "vM3=>$vm3")
        Log.d("test123", "vM4=>$vm4")
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).store.clear()
    }
}
