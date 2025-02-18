package com.ytxmmie.ytmodthird.client;

import com.ytxmmie.ytmodthird.entity.PrinterBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.Objects;

public class PrinterBlockEntityRenderer <T extends PrinterBlockEntity> implements BlockEntityRenderer<T> {

    public PrinterBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super();
    }

    @Override
    public void render(PrinterBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {


        World world = blockEntity.getWorld();

        int worldLight = 0;
        if(world != null) {
            worldLight = Math.max(
                    Math.max(
                            WorldRenderer.getLightmapCoordinates(world, blockEntity.getPos().north()),
                            WorldRenderer.getLightmapCoordinates(world, blockEntity.getPos().south())
                    ),
                    Math.max(
                            Math.max(
                                    WorldRenderer.getLightmapCoordinates(world, blockEntity.getPos().east()),
                                    WorldRenderer.getLightmapCoordinates(world, blockEntity.getPos().west())
                            ),
                            WorldRenderer.getLightmapCoordinates(world, blockEntity.getPos().up())
                    )
            );
        }
        ItemStack inStack = blockEntity.getStack(PrinterBlockEntity.SLOT_INPUT).copy();
        ItemStack outStack = blockEntity.getStack(PrinterBlockEntity.SLOT_OUTPUT).copy();
        int allCount = inStack.getCount();
        int outcount = outStack.getCount();
        int processCount = 0;
        float angle = 0f;
        ItemStack resultStack = new ItemStack(Items.AIR);
        if(blockEntity.result == null){
            processCount = 0;
        }
        else{
            processCount = blockEntity.result.getCount();
            resultStack = blockEntity.result.copy();
        }
        int progress = blockEntity.processProgress;
        int i = blockEntity.FACING;
        if(blockEntity.FACING == 1) {
            if((allCount - processCount) > 0)
            {
                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
                matrices.translate(0.5f, 4.25f / 16f, 3f / 16f + (allCount - processCount) * (1f / 16f) * (1f / 64f) * (1f / 2f) - 1f);
                matrices.scale(0.25f, 0.25f, (allCount - processCount) / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();


                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
                matrices.translate(0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f , 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f) - 1f);
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            else if(allCount != 0){

                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
                matrices.translate(0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f, 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f) - 1f);
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            matrices.translate(0.5f, 1.5f / 16f, (1f / 16f + outcount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            matrices.scale(0.25f, 0.25f, outcount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(outStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            if(progress <= 50) {
                matrices.translate(0.5f, (7f / 16f - (int) (progress / 10) * 0.5 / 16f), (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            else{
                matrices.translate(0.5f, (4.5f / 16f -  (progress - 50) * 0.3 / 16f), (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            matrices.scale(0.25f, 0.25f, (float)processCount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(resultStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();


        }
        if(blockEntity.FACING == 2) {
            if((allCount - processCount) > 0)
            {
                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                matrices.translate(-0.5f, 4.25f / 16f, 3f / 16f + (allCount - processCount) * (1f / 16f) * (1f / 64f) * (1f / 2f));
                matrices.scale(0.25f, 0.25f, (float)(allCount - processCount) / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();


                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                matrices.translate(-0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f , 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f));
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            else if(allCount != 0){

                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                matrices.translate(-0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f, 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f));
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            matrices.translate(-0.5f, -14.5f / 16f, (1f / 16f + outcount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            matrices.scale(0.25f, 0.25f, (float) outcount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(outStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            if(progress <= 50) {
                matrices.translate(-0.5f, -(9f / 16f + (int) (progress / 10) * 0.5 / 16f), (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            else{
                matrices.translate(-0.5f, -(11.5f / 16f +  (progress - 50) * 0.3 / 16f), (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(resultStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();


        }
        if(blockEntity.FACING == 3) {
            if((allCount - processCount) > 0)
            {
                matrices.push();
                matrices.translate(0.5f, 4.25f / 16f, 3f / 16f + (allCount - processCount) * (1f / 16f) * (1f / 64f) * (1f / 2f));
                matrices.scale(0.25f, 0.25f, (allCount - processCount) / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.scale(4f, 4f, 64f / (allCount - processCount));
                matrices.translate(0.5f, -4.25f / 16f, -(3f / 16f + (allCount - processCount) * (1f / 16f) * (1f / 64f) * (1f / 2f)));
                matrices.pop();


                matrices.push();
                matrices.translate(0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f , 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f));
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            else if(allCount != 0){

                matrices.push();
                matrices.translate(0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f, 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f));
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            matrices.translate(0.5f, -14.5f/16f, (1f / 16f + outcount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            matrices.scale(0.25f, 0.25f, (float)outcount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(outStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            if(progress <= 50) {
                matrices.translate(0.5f, -(9f / 16f + (int) (progress / 10) * 0.5 / 16f), (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            else{
                matrices.translate(0.5f, -(11.5f / 16f +  (progress - 50) * 0.3 / 16f), (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(resultStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();


        }
        if(blockEntity.FACING == 4) {
            if((allCount - processCount) > 0)
            {
                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
                matrices.translate(-0.5f, 4.25f / 16f, 3f / 16f + (allCount - processCount) * (1f / 16f) * (1f / 64f) * (1f / 2f) - 1f);
                matrices.scale(0.25f, 0.25f, (allCount - processCount) / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();


                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
                matrices.translate(-0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f , 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f) - 1f);
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            else if(allCount != 0){

                matrices.push();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
                matrices.translate(-0.5f, 4.25f / 16f - ((int) (Math.min(progress, 50) / 10) * 0.5f) / 16f, 3f / 16f + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f) + (allCount - processCount) * (1f / 16f) * (1f / 64f) - 1f);
                matrices.scale(0.25f, 0.25f, (float) processCount / 64f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(inStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            matrices.translate(-0.5f, -14.5f/16f + 1f, (1f / 16f + outcount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            matrices.scale(0.25f, 0.25f, (float)outcount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(outStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            if(progress <= 50) {
                matrices.translate(-0.5f, -(9f / 16f + (int) (progress / 10) * 0.5 / 16f) + 1f, (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            else{
                matrices.translate(-0.5f, -(11.5f / 16f +  (progress - 50) * 0.3 / 16f) + 1f, (1f / 16f + outcount * (1f / 16f) * (1f / 64f) + processCount * (1f / 16f) * (1f / 64f) * (1f / 2f)));
            }
            matrices.scale(0.25f, 0.25f, (float)processCount / 64f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(resultStack, ModelTransformationMode.NONE, worldLight, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();


        }
    }
}
