package io.github.grishaninvyacheslav.core_ui.presentation

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

abstract class ListAdapter(adapterDelegate: AdapterDelegate<List<DisplayableItem>>) :
    ListDelegationAdapter<List<DisplayableItem>>(adapterDelegate)