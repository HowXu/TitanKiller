package com.howxu.demo.Event;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import com.howxu.demo.CoreMod.DemoCore;

import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;

public class RegenUtil {
        //尝试地形复写功能

    public static void Regen(World world,int x,int z){
        IChunkProvider provider = world.getChunkProvider();//获得区块供应
        if(provider instanceof ChunkProviderServer){
                ChunkProviderServer providerServer = (ChunkProviderServer) provider;
                try{
                    //DemoCore.Debug = true;
                    Field ChunksToUnload = ChunkProviderServer.class.getDeclaredField(DemoCore.Debug ? "chunksToUnload" : "field_73248_b");
                    Field LoadedChunkHashMap = ChunkProviderServer.class.getDeclaredField(DemoCore.Debug ? "LoadedChunkHashMap" : "field_73244_f");
                    Field LoadedChunks = ChunkProviderServer.class.getDeclaredField(DemoCore.Debug ? "LoadedChunks" : "field_73245_g");
                    //上面三个用于卸载，加载，刷新区块
                    Field CurrentChunkProvider = ChunkProviderServer.class.getDeclaredField(DemoCore.Debug ? "CurrentChunkProvider" : "field_73246_d");
                    //用于构造最初的地形
                    ChunksToUnload.setAccessible(true);
                    LoadedChunkHashMap.setAccessible(true);
                    LoadedChunks.setAccessible(true);
                    CurrentChunkProvider.setAccessible(true);

                    Set<?> UnloadQueue = (Set<?>) ChunksToUnload.get(providerServer);
                    LongHashMap LoadedHashMap = (LongHashMap) LoadedChunkHashMap.get(providerServer);
                    List<Chunk> Loaded = (List<Chunk>) LoadedChunks.get(providerServer);
                    IChunkProvider chunkProvider = (IChunkProvider) CurrentChunkProvider.get(providerServer);

                    Chunk chunk = world.getChunkFromBlockCoords(x, z);//根据x和z获取一个区块
                    if(providerServer.chunkExists(chunk.xPosition, chunk.xPosition)){
                        //如果区块已经存在，则卸载
                        Chunk c = providerServer.loadChunk(chunk.xPosition, chunk.xPosition);
                        c.onChunkLoad();
                    }

                    long Pos = ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.xPosition);
                    //卸载
                    UnloadQueue.remove(Long.valueOf(Pos));
                    LoadedHashMap.remove(Pos);
                    //调用provide生成最初的地形
                    Chunk RegeneratedChunk = chunkProvider.provideChunk(chunk.xPosition, chunk.xPosition);
                    //加载
                    LoadedHashMap.add(Pos, RegeneratedChunk);
                    Loaded.add(RegeneratedChunk);
                    //加载并填充
                    RegeneratedChunk.onChunkLoad();
                    RegeneratedChunk.populateChunk(chunkProvider, chunkProvider,chunk.xPosition, chunk.xPosition);
                }catch(Exception e){

                }
        }
    }

}
