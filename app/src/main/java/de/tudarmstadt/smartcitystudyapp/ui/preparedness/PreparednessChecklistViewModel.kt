package de.tudarmstadt.smartcitystudyapp.ui.preparedness

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import de.tudarmstadt.smartcitystudyapp.interfaces.PreparednessChecklistServiceInterface

class PreparednessChecklistViewModel @ViewModelInject constructor(
    val preparednessChecklistServiceInterface: PreparednessChecklistServiceInterface
) : ViewModel() {

}