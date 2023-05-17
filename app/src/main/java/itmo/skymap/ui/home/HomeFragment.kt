package itmo.skymap.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import itmo.skymap.MainActivity
import itmo.skymap.R
import itmo.skymap.ui.home.components.NavigationCard
import itmo.skymap.ui.home.components.NavigationCardData

/*
 * @author Ivan Kozlov
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets {
                        HomeScreen(
                            onNavCardClick = { action -> findNavController().navigate(action) },
                            onMenuClick = { (activity as MainActivity).openDrawer() },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onMenuClick: () -> Unit,
    onNavCardClick: (action: NavDirections) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .statusBarsPadding()
            ) {
                Icon(Icons.Filled.Menu, "")
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Card(
            shape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(40.dp),
                topEnd = CornerSize(40.dp)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
                Text(
                    text = "Explore",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                val cardModifier =
                    Modifier
                        .aspectRatio(1f)
                        .padding(6.dp)
                val navDataList = listOf(
                    NavigationCardData(
                        painter = painterResource(R.drawable.venus_background),
                        title = stringResource(R.string.space_nav),
                        modifier = cardModifier,
                        onClick = {
                            onNavCardClick(HomeFragmentDirections.actionHomeFragmentToSpaceNavigatorActivity())
                        }
                    ),
                )
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        navDataList.take(3).forEach {
                            NavigationCard(data = it)
                        }
                    }
                }
            }
        }
    }
}
