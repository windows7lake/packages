import 'dart:io';

import 'package:file_picker/file_picker.dart' as file_picker;
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart' as image_picker;
import 'package:path_provider/path_provider.dart';

import 'android_webview_controller.dart';

/// filePickerHandler
Future<List<String>> filePickerHandler(FileSelectorParams params) async {
  debugPrint('FileSelectorParams: ${params.acceptTypes}  '
      '${params.filenameHint}  ${params.isCaptureEnabled}  ${params.mode}');
  if (params.acceptTypes.any((String type) => type == 'image/*')) {
    return filePickerImage(params);
  } else if (params.acceptTypes.any((String type) => type == 'video/*')) {
    return filePickerVideo(params);
  } else {
    return filePickerOthers(params);
  }
}

/// filePicker Image
Future<List<String>> filePickerImage(FileSelectorParams params) async {
  final image_picker.ImagePicker picker = image_picker.ImagePicker();
  final image_picker.ImageSource source = params.isCaptureEnabled
      ? image_picker.ImageSource.camera
      : image_picker.ImageSource.gallery;
  if (params.mode == FileSelectorMode.openMultiple) {
    final List<image_picker.XFile> photos = await picker.pickMultiImage();
    if (photos == null) {
      return [];
    }

    final List<String> photosList = [];
    for (final image_picker.XFile photo in photos) {
      final Uint8List imageData = await photo.readAsBytes();
      final Uri filePath = (await getTemporaryDirectory()).uri.resolve(
            './${DateTime.now().microsecondsSinceEpoch}${photo.name}',
          );
      final File file = await File.fromUri(filePath).create(recursive: true);
      await file.writeAsBytes(imageData, flush: true);
      photosList.add(file.uri.toString());
    }
    return photosList;
  } else {
    final image_picker.XFile? photo = await picker.pickImage(source: source);
    if (photo == null) {
      return [];
    }

    final Uint8List imageData = await photo.readAsBytes();
    final Uri filePath = (await getTemporaryDirectory()).uri.resolve(
          './${DateTime.now().microsecondsSinceEpoch}${photo.name}',
        );
    final File file = await File.fromUri(filePath).create(recursive: true);
    await file.writeAsBytes(imageData, flush: true);

    return [file.uri.toString()];
  }
}

/// filePicker Video
Future<List<String>> filePickerVideo(FileSelectorParams params) async {
  final image_picker.ImagePicker picker = image_picker.ImagePicker();
  final image_picker.ImageSource source = params.isCaptureEnabled
      ? image_picker.ImageSource.camera
      : image_picker.ImageSource.gallery;
  final image_picker.XFile? video = await picker.pickVideo(source: source);
  if (video == null) {
    return [];
  }

  final Uint8List videoData = await video.readAsBytes();
  final Uri filePath = (await getTemporaryDirectory()).uri.resolve(
        './${DateTime.now().microsecondsSinceEpoch}${video.name}',
      );
  final File file = await File.fromUri(filePath).create(recursive: true);
  await file.writeAsBytes(videoData, flush: true);

  return [file.uri.toString()];
}

/// filePicker Others
Future<List<String>> filePickerOthers(FileSelectorParams params) async {
  final file_picker.FilePickerResult? result = await file_picker
      .FilePicker.platform
      .pickFiles(allowMultiple: params.mode == FileSelectorMode.openMultiple);

  if (result != null) {
    final List<File> files =
        result.paths.map((String? path) => File(path!)).toList();
    return files
        .map((File file) => file.uri.toString())
        .toList(growable: false);
  }
  return [];
}
