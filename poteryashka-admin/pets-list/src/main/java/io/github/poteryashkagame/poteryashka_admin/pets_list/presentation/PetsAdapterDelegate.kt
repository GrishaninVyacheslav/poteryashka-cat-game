package io.github.poteryashkagame.poteryashka_admin.pets_list.presentation

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.grishaninvyacheslav.core_ui.presentation.DisplayableItem
import io.github.poteryashkagame.poteryashka_admin.pets_list.data.entity.PetDisplayableItemEntity
import io.github.poteryashkagame.poteryashka_admin.pets_list.databinding.ItemPetBinding

class PetsAdapterDelegate {
    val adapterDelegate =
        adapterDelegateViewBinding<PetDisplayableItemEntity, DisplayableItem, ItemPetBinding>(
            { layoutInflater, root -> ItemPetBinding.inflate(layoutInflater, root, false) }
        ) {
            binding.root.setOnClickListener {
                onClickEvent?.invoke(item)
            }
            bind {
                with(binding) {
                    Glide
                        .with(photo)
                        .load(item.photoUrl)
                        .into(photo)

                    name.text = item.name
                    age.text = item.age.toString()
                }
            }
        }

    var onClickEvent: ((PetDisplayableItemEntity) -> Unit)? = null
}