package itmo.skymap.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.sentry.Sentry
import io.sentry.UserFeedback
import itmo.skymap.utils.Data

/*
 * @author Ivan Kozlov
 */
class ContactViewModelImpl : ViewModel(), ContactViewModel {

    override fun sendFeedback(
        title: String,
        name: String,
        email: String,
        text: String
    ): LiveData<Data<Boolean>> {
        val returnData = MutableLiveData<Data<Boolean>>()
        try {
            val sentry = Sentry.captureMessage(title)
            val feedback = UserFeedback(sentry)
            feedback.name = name
            feedback.email = email
            feedback.comments = text
            Sentry.captureUserFeedback(feedback)
            returnData.postValue(Data.Ok(true))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }
}
