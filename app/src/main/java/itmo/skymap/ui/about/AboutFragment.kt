package itmo.skymap.ui.about

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import itmo.skymap.R
import itmo.skymap.databinding.FragmentAboutBinding

/*
 * @author Ivan Kozlov
 */
@AndroidEntryPoint
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //println(isNetworkAvailable(context))
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        loadImageCircle(requireContext(), ANIMATED_DEEP_SPACE, binding.logoGif)
        setupDevelopersData()
    }

    private fun setupDevelopersData() {
        with(binding) {
            IvanMail.setOnClickListener {
                copyToClipBoard(IVAN_MAIL, binding.IvanGladkikh.text.toString())
            }
            IvanVk.setOnClickListener {
                openLink(IVAN_VK)
            }
            IvanGit.setOnClickListener {
                openLink(IVAN_GIT)
            }
        }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun copyToClipBoard(text: String, name: String) {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("email", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), getString(R.string.copiedToClipboard, name), Toast.LENGTH_SHORT).show()
    }

    companion object Links {
        const val IVAN_VK = "https://vk.com/ivankozlov98"
        const val IVAN_MAIL = "ivan1998olimp@yandex.ru"
        const val IVAN_GIT = "https://github.com/IvanKozlov98"
    }
}
