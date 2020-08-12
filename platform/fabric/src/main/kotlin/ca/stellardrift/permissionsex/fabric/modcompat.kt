/*
 * PermissionsEx
 * Copyright (C) zml and PermissionsEx contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.stellardrift.permissionsex.fabric

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.event.platform.PlatformReadyEvent
import com.sk89q.worldedit.fabric.FabricPermissionsProvider
import com.sk89q.worldedit.fabric.FabricWorldEdit
import com.sk89q.worldedit.util.eventbus.Subscribe
import net.minecraft.server.network.ServerPlayerEntity

class PEXWorldEditProvider : FabricPermissionsProvider {
    override fun registerPermission(permission: String) {
        // no-op
    }

    override fun hasPermission(subject: ServerPlayerEntity, permission: String): Boolean {
        return subject.hasPermission(permission, 4)
    }

    @Subscribe
    fun onPlatformReady(event: PlatformReadyEvent) {
        FabricWorldEdit.inst.permissionsProvider = this
        PermissionsExMod.logger.info(Messages.INTEGRATION_REGISTER_SUCCESS("WorldEdit"))
    }
}

fun registerWorldEdit(): Boolean {
    return try {
        Class.forName("com.sk89q.worldedit.fabric.FabricWorldEdit")
        WorldEdit.getInstance().eventBus.register(PEXWorldEditProvider())
        true
    } catch (ex: ClassNotFoundException) {
        false
    }
}
