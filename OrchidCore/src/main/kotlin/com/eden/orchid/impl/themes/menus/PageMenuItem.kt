package com.eden.orchid.impl.themes.menus

import com.eden.common.util.EdenUtils
import com.eden.orchid.api.OrchidContext
import com.eden.orchid.api.options.annotations.Description
import com.eden.orchid.api.options.annotations.Option
import com.eden.orchid.api.theme.menus.OrchidMenuFactory
import com.eden.orchid.api.theme.menus.MenuItem
import com.eden.orchid.api.theme.pages.OrchidPage
import java.util.ArrayList
import javax.inject.Inject

@Description("A page in your site, referenced from a Collection. If no page query is given, will use the current page.",
        name = "Page"
)
class PageMenuItem
@Inject
constructor(
        context: OrchidContext
) : OrchidMenuFactory(context, "page", 100) {

    @Option
    @Description("The title of this menu item.")
    lateinit var title: String

    @Option
    @Description("The Id of an item to look up.")
    lateinit var itemId: String

    @Option
    @Description("The type of collection the item is expected to come from.")
    lateinit var collectionType: String

    @Option
    @Description("The specific Id of the given collection type where the item is expected to come from.")
    lateinit var collectionId: String

    override fun getMenuItems(): List<MenuItem> {
        val menuItems = ArrayList<MenuItem>()

        val page: OrchidPage = (
                if (!EdenUtils.isEmpty(collectionType) || !EdenUtils.isEmpty(collectionId) || !EdenUtils.isEmpty(itemId))
                    context.findPage(collectionType, collectionId, itemId)
                else
                    null
                ) ?: this.page

        val item = MenuItem.Builder(context).page(page)
        if (!EdenUtils.isEmpty(title)) {
            item.title(title)
        }
        menuItems.add(item.build())

        return menuItems
    }
}
