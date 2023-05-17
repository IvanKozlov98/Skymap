package itmo.skymap.ui.contact

import androidx.lifecycle.LiveData
import itmo.skymap.utils.Data

/*
 * @author Ivan Kozlov
 */
interface ContactViewModel {
    fun sendFeedback(title: String, name: String, email: String, text: String): LiveData<Data<Boolean>>
}
