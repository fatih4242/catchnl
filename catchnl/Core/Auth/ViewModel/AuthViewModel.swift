//
//  AuthViewModel.swift
//  catchnl
//
//  Created by Fatih Toker on 13.09.2023.
//

import Foundation
import PhotosUI
import SwiftUI
import Alamofire

enum UploadState{
    case nothing
    case loading
}

class AuthViewModel: ObservableObject {
    @Published var state : UploadState = .nothing
    
    @Published var name = ""
    @Published var email = ""
    @Published var password = ""
    @Published var licencePlate = ""
    
    @Published var selectedItem: PhotosPickerItem?
    @Published var selectedImage: Image?
    @Published var selectedImageData: Data?
    
    @Published var isError = false
    @Published var errorMessage = ""
    
    func signUp(_ isSuccess: @escaping(Bool) -> Void){
        
        if email.isEmpty {
            errorMessage(true, "vul het emailbox in")
            isSuccess(false)
        } else if name.isEmpty {
            errorMessage(true, "vul het naambox in")
            isSuccess(false)
        } else if password.isEmpty {
            errorMessage(true, "vul het wachtwoordbox in")
            isSuccess(false)
        } else if licencePlate.isEmpty {
            errorMessage(true, "vul het kentekenbox in")
            isSuccess(false)
        } else if selectedItem == nil
            && selectedImage == nil
            && selectedImageData == nil {
            
            errorMessage(true, "kies een kentekenbewijs foto")
            isSuccess(false)
            
        } else {

            let parameter = [
                "name" : self.name,
                "email" : self.email,
                "password" : self.password,
                "licencePlate" : self.licencePlate,
                "licenceFile" : selectedImageData!.base64EncodedString()
            ]
            
            API().MakeRequest(endpoint: URLState.register.rawValue , parameters: parameter) { data in
                let register = try! JSONDecoder().decode(ErrorMessageUserID.self, from: data)
                if register.error == 0 {
                    UserDefaults.standard.setValue(self.email, forKey: "email")
                    UserDefaults.standard.setValue(self.password, forKey: "password")
                    isSuccess(true)
                } else {
                    isSuccess(false)
                    self.errorMessage(true, register.message)
                }
            } onError: { error in
                self.errorMessage(true, error)
                isSuccess(false)
            }
        }
    }
    
    func signIn(_ isSuccess: @escaping(Bool) -> Void) {
        if email.isEmpty {
            errorMessage(true, "vul het emailbox in")
            isSuccess(false)
        } else if password.isEmpty {
            errorMessage(true, "vul het wachtwoordbox in")
            isSuccess(false)
        } else {
            let parameter = [
                "email": email,
                "password": password
            ]
      
            
            API().MakeRequest(endpoint: URLState.login.rawValue, parameters: parameter, onSuccess: { data in
                
                let login = try! JSONDecoder().decode(ErrorMessageUserID.self, from: data)
                if login.error == 0 {
                    UserDefaults.standard.setValue(self.email, forKey: "email")
                    UserDefaults.standard.setValue(self.password, forKey: "password")
                    isSuccess(true)
                } else {
                    self.errorMessage(true, login.message)
                    isSuccess(false)
                }
                
            }, onError: { error in
                self.errorMessage(true, error)
                isSuccess(false)
            })
             
        }
    }
    
    func errorMessage(_ isError: Bool,_ errorMessage: String){
        self.isError = isError
        self.errorMessage = errorMessage
    }
    
    func resetPassword(_ isSuccess: @escaping(Bool) -> Void){
        if self.email.isEmpty {
            isSuccess(false)
            errorMessage(true, "vul het emailadressbox in")
        } else {
            let parameter = [
                "email" : self.email
            ]
            
            API().MakeRequest(endpoint: URLState.resetPassword.rawValue, parameters: parameter, onSuccess: { data in
                let resetPass = try! JSONDecoder().decode(ErrorMessageUserID.self, from: data)
                if resetPass.error == 0 {
                    isSuccess(true)
                } else {
                    isSuccess(false)
                    self.errorMessage(true, resetPass.message)
                }
            }, onError: { error in
                isSuccess(false)
                self.errorMessage(true, error)
            })
        }
    }
}
