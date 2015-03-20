/*
 * Copyright 2013 Xtreme Labs
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xtremelabs.imageutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import android.graphics.Bitmap;

import com.xtremelabs.imageutils.DiskLRUCacher.FileFormatException;

public class DiskCacheStub implements ImageDiskCacherInterface {
	@Override
	public void downloadImageFromInputStream(String uri, InputStream inputStream) throws IOException {
	}

	@Override
	public void bumpOnDisk(String uri) {
	}

	@Override
	public void setDiskCacheSize(long sizeInBytes) {
	}

	@Override
	public void invalidateFileSystemUri(String uri) {
	}

	@Override
	public int getSampleSize(CacheRequest imageRequest) {
		return 0;
	}

	@Override
	public Prioritizable getDetailsPrioritizable(CacheRequest imageRequest) {
		return null;
	}

	@Override
	public Prioritizable getDecodePrioritizable(CacheRequest cacheRequest, DecodeSignature decodeSignature, ImageReturnedFrom imageReturnedFrom) {
		return null;
	}

	@Override
	public boolean isCached(CacheRequest cacheRequest) {
		return false;
	}

	@Override
	public Dimensions getImageDimensions(CacheRequest cacheRequest) {
		return null;
	}

	@Override
	public Bitmap getBitmapSynchronouslyFromDisk(CacheRequest cacheRequest, DecodeSignature decodeSignature) throws FileNotFoundException, FileFormatException {
		return null;
	}

	@Override
	public void calculateAndSaveImageDetails(CacheRequest cacheRequest) throws URISyntaxException, FileNotFoundException {
	}
}
