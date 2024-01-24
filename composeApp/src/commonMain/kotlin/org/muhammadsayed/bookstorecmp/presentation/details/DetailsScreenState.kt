package org.muhammadsayed.bookstorecmp.presentation.details

import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDetailsData

sealed class DetailsScreenState {
    data object Loading : DetailsScreenState()
    sealed class Success : DetailsScreenState() {
        data class BookDetails(val data: BookDetailsData) : Success()
    }

    data class Error(val error: String) : DetailsScreenState()
}