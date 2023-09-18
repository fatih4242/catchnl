//
//  ProfileViewModel.swift
//  catchnl
//
//  Created by Fatih Toker on 15.09.2023.
//

import Foundation

class ProfileViewModel: ObservableObject {
    @Published var state: UploadState = .nothing
    
    @Published var isError = false
    @Published var errorMessage = ""
    
    @Published var licencePlate = ""
    @Published var name = ""
    @Published var email = ""
    @Published var userID = ""
    
    
    func updateUserData(_ onSuccess: @escaping(Bool) -> Void){
        if licencePlate.isEmpty {
            self.errorMessage(true, "vul het kentekenbox in")
            onSuccess(false)
        } else if name.isEmpty {
            self.errorMessage(true, "vul het naambox in")
            onSuccess(false)
        } else if email.isEmpty {
            self.errorMessage(true, "vul het emailbox in")
            onSuccess(false)
        } else {
            updateUser(onSuccess)
        }
    }
    
    private func updateUser(_ onSuccess: @escaping(Bool) -> Void){
        let parameter = [
            "userID" : self.userID,
            "email" : self.email,
            "name" : self.name,
            "licencePlate" : self.licencePlate
        ]
        
        API().MakeRequest(endpoint: URLState.updateUser.rawValue, parameters: parameter) { data in
            let updateUser = try! JSONDecoder().decode(ErrorMessageUserID.self, from: data)
            if updateUser.error == 0 {
                print("updated \(updateUser.message)")
                onSuccess(true)
            } else {
                self.errorMessage(true, updateUser.message)
                onSuccess(false)
            }
                
        } onError: { error in
            self.errorMessage(true, error)
            onSuccess(false)
        }

    }
    
    func signOut(){
        UserDefaults.standard.setValue("", forKey: "email")
        UserDefaults.standard.setValue("", forKey: "password")
    }
    
    func errorMessage(_ isError: Bool,_ errorMessage: String){
        self.isError = isError
        self.errorMessage = errorMessage
    }
}
